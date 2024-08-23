package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.SendResult;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailProducerService {

    @Autowired
    private KafkaTemplate<String, EmailDto> kafkaTemplate;

    // kafka producer to topic message
    public void produce(EmailDto data) {
        Message<EmailDto> message = MessageBuilder.withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "email")
                .build();
        CompletableFuture< SendResult<String,EmailDto> > completableFuture =  kafkaTemplate.send(message);
        completableFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Unable to send message: " + ex.getMessage());
            } else {
                System.out.println("Sent message: " + data.getSubject());
            }
        });
    }
}
