package main

import (
	"context"
	"fmt"
	"log"
	"strings"
	"sync"
	"time"

	kafka "github.com/segmentio/kafka-go"
)

func getKafkaReader(kafkaURL, topic, groupID string) *kafka.Reader {
	brokers := strings.Split(kafkaURL, ",")
	return kafka.NewReader(kafka.ReaderConfig{
		Brokers:  brokers,
		GroupID:  groupID,
		Topic:    topic,
		MinBytes: 10e3, // 10KB
		MaxBytes: 10e6, // 10MB
	})
}

func getKafkaWriter(kafkaURL, topic string) *kafka.Writer {
	return &kafka.Writer{
		Addr:     kafka.TCP(kafkaURL),
		Topic:    topic,
		Balancer: &kafka.LeastBytes{},
	}
}

// Create a non-blocking retryable topic consumer that reads messages from the main topic and retries processing
// the message in case of an error. The retry logic is implemented using a backoff duration that increases
// exponentially with each retry attempt. The consumer function is called with each message read from the topic chain.
func NonBlockingRetryableTopic(consumer func(kafka.Message) error, mainTopic string, attempts int, backoff time.Duration, multiplier float32, suffix_strategy string, concurrency int) {

	// create a string slice to store the topics, first one is the main topic, the rest are the retry topics based on
	// the number of attempts and the suffix strategy and the last one is the dead letter topic
	topics := make([]string, attempts+1)
	topics[0] = mainTopic
	for i := 1; i < attempts; i++ {
		topics[i] = fmt.Sprintf("%s%s-retry-%d", mainTopic, suffix_strategy, i-1)
	}
	topics[attempts] = fmt.Sprintf("%s-dlt", mainTopic)

	// spawn a goroutine for each topic
	expotential_backoff := backoff

	for i := 0; i < concurrency; i++ {
		go retryableTopic(topics[0], topics[1], consumer, expotential_backoff)
	}
	expotential_backoff = time.Duration(float32(expotential_backoff) * multiplier)
	for i := 1; i < attempts; i++ {
		go retryableTopic(topics[i], topics[i+1], consumer, expotential_backoff)
		expotential_backoff = time.Duration(float32(expotential_backoff) * multiplier)
	}

}

func retryableTopic(topic, retryTopic string, consumer func(kafka.Message) error, backoff time.Duration) {
	// Kafka reader configuration
	kafkaURL := "localhost:29092"
	reader := getKafkaReader(kafkaURL, topic, "consumer-go")
	defer reader.Close()

	// Kafka writer configuration for the retry topic
	writer := getKafkaWriter(kafkaURL, retryTopic)
	defer writer.Close()

	var mutex sync.Mutex

	for {
		message, err := reader.ReadMessage(context.Background())
		if err != nil {
			log.Printf("failed to read message from topic %s: %v", topic, err)
			continue
		}

		// Process the message
		err = consumer(message)
		if err != nil {
			log.Printf("error processing message, scheduling retry: %v", err)

			// Start a new goroutine to handle retry after backoff
			go func(msg kafka.Message, writer *kafka.Writer, retryTopic string) {
				// Wait for the backoff duration
				time.Sleep(backoff)
				// writer := getKafkaWriter(kafkaURL, retryTopic)
				// defer writer.Close()

				// Publish the message to the retry topic
				mutex.Lock()
				err = writer.WriteMessages(context.Background(), kafka.Message{
					Key:     msg.Key,
					Value:   msg.Value,
					Headers: msg.Headers, // Preserve headers if any
				})
				mutex.Unlock()
				if err != nil {
					log.Printf("failed to write message to retry topic %s: %v", retryTopic, err)
				} else {
					log.Printf("Message sent to retry topic %s after backoff: %s", retryTopic, string(msg.Value))
				}
			}(message, writer, retryTopic)

		} else {
			log.Printf("Successfully processed message: %s", string(message.Value))
		}
	}
}
