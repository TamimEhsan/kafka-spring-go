package com.example.consumer2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    
    @RetryableTopic(attempts = "2" )// 1 retries
    @KafkaListener(topics = "message", groupId = "consumer2")
    public void consumeMessage(String message) throws Exception {
        System.out.println("Received message: " + message);
        if( message.equals("error2") ) throw new Exception("Error processing message");
        if( message.equals("error") ) throw new Exception("Error processing message");
        System.out.println("Processed message: " + message);
        
    }
}
