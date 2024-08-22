package com.example.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

import com.example.common.EmailDto;
import com.example.common.PersonDto;

@Component
public class KafkaConsumer {
    
    // @RetryableTopic(attempts = "2" )// 1 retries
    @KafkaListener(topics = "message", groupId = "consumer2", concurrency = "1")
    public void consumeMessage(PersonDto message) throws Exception {
        System.out.println("Received message: " + message.getName());
        // if( message.equals("error2") ) throw new Exception("Error processing message");
        // if( message.equals("error") ) throw new Exception("Error processing message");
        // System.out.println("Processed message: " + message);
        
    }

    @RetryableTopic(attempts = "1" )// 1 retries
    @KafkaListener(topics = "email", groupId = "consumer2", concurrency = "1")
    public void consumeMessage(EmailDto message) throws Exception {
        System.out.println("Received message: " + message.getTo());
        if( message.getTo().equals("error") ) throw new Exception("Error processing message");
        // if( message.equals("error2") ) throw new Exception("Error processing message");
        // if( message.equals("error") ) throw new Exception("Error processing message");
        // System.out.println("Processed message: " + message);
        
    }
}
