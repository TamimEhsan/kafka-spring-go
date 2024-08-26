package com.example.producer;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Object payload) {
         Message<Object> message = MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        CompletableFuture< SendResult<String,Object> > completableFuture = kafkaTemplate.send(message);
        completableFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Unable to send message: " + ex.getMessage());
            } else {
                System.out.println("Sent message: " + payload.toString());
            }
        });
    }

    
}
