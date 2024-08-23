package main

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"strings"

	kafka "github.com/segmentio/kafka-go"
)

type EmailDto struct {
	To      string `json:"to"`
	Subject string `json:"subject"`
}

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

func main() {
	// get kafka reader using environment variables.
	kafkaURL := "localhost:29092"
	topic := "email"
	groupID := "consumer-go"

	reader := getKafkaReader(kafkaURL, topic, groupID)

	defer reader.Close()

	fmt.Println("start consuming ... !!")
	for {
		message, err := reader.ReadMessage(context.Background())
		if err != nil {
			log.Fatalln(err)
		}

		var email EmailDto
		err = json.Unmarshal(message.Value, &email)
		if err != nil {
			log.Fatalln(err)
			continue
		}

		fmt.Printf("Received message: Key=%s To=%s Subject=%s\n", string(message.Key), email.To, email.Subject)
	}
}
