package main

import (
	"encoding/json"
	"fmt"
	"log"
	"time"

	kafka "github.com/segmentio/kafka-go"
)

type EmailDto struct {
	To      string `json:"to"`
	Subject string `json:"subject"`
}

func main() {
	fmt.Println("Starting consumer...")
	// get kafka reader using environment variables.

	NonBlockingRetryableTopic(processMessage, "email", 3, 1*time.Second, 2, "-consumer-go", 1)

	select {}

}

// Example consumer function
func processMessage(message kafka.Message) error {
	// unmarshal the message value to EmailDto
	var email EmailDto = EmailDto{}
	err := json.Unmarshal(message.Value, &email)
	if err != nil {
		return fmt.Errorf("failed to unmarshal message value: %v", err)
	}
	// Simulate processing logic
	if string(email.To) == "error" {
		return fmt.Errorf("simulated processing error for message: %s", string(message.Value))
	}

	log.Printf("Processing message: Key=%s, Value=%s", string(message.Key), string(message.Value))
	return nil
}
