package com.example.consumer1;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class kafkaConsumer {
    
    @KafkaListener(topics = "message", groupId = "consumer1")
    public void consumeMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
