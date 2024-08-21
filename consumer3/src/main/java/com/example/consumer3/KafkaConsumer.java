package com.example.consumer3;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    /*
     * When a message fails to be delivered to its intended topic, it’ll be automatically sent to the retry topic for retrying.
     * If the message still can’t be delivered after the maximum number of attempts, it’ll be sent to the DLT for further processing.
     */
    @RetryableTopic(attempts = "2" )// 1 retries
    @KafkaListener(topics = "message", groupId = "consumer2")
    public void consumeMessage(String message) throws Exception {
        System.out.println("Received message: " + message);
        if( message.equals("error") ) throw new Exception("Error processing message");
        System.out.println("Processed message: " + message);
    }

    // public void processMessage (String message) throws Exception {
    //     // create a new exception randomly
    //     if (Math.random() < 0.5) {
    //         throw new Exception("Error processing message");
    //     }
    //     System.out.println("Processing message: " + message);
    // }

    // @DltHandler
    // public void listenDLT(String message, Acknowledgment acknowledgment) {
    //     System.out.println("Received message from DLT: " + message);
    // }
}
