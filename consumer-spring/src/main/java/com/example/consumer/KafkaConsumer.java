package com.example.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff; // Add this import statement
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    
    // @RetryableTopic(attempts = "2" )// 1 retries
    @KafkaListener(topics = "message", groupId = "consumer", concurrency = "1")
    public void consumeMessage(PersonDto message) throws Exception {
        System.out.println("Received message: " + message.getName() + " in thread: " + Thread.currentThread().threadId());
        // if( message.equals("error2") ) throw new Exception("Error processing message");
        // if( message.equals("error") ) throw new Exception("Error processing message");
        // System.out.println("Processed message: " + message);
        
    }

    @RetryableTopic(attempts = "3",
                    backoff = @Backoff(delay = 1000, multiplier = 2.0),
                    retryTopicSuffix = "-consumer-retry"
                ) // Use @Backoff as the value for backoff attribute
    @KafkaListener(topics = "email", groupId = "consumer", concurrency = "1")
    public void consumeMessage(EmailDto message) throws Exception{
        System.out.println("Received message: " + message.getTo()+ " in thread: " + Thread.currentThread().threadId());
        
        if( message.getTo().equals("error") ) throw new Exception("Error processing message");
        if( message.getTo().equals("error1") ) throw new Exception("Error processing message");
        
    }
}
