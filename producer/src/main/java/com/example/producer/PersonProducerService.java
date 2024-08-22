package com.example.producer;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.common.PersonDto;

@Service
public class PersonProducerService {

    @Autowired
    private KafkaTemplate<String, PersonDto> kafkaTemplate;

    // kafka producer to topic message
    public void produce(PersonDto data) {
        Message<PersonDto> message = MessageBuilder.withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "message")
                .build();
        CompletableFuture< SendResult<String,PersonDto> > completableFuture = kafkaTemplate.send(message);
        completableFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Unable to send message: " + ex.getMessage());
            } else {
                System.out.println("Sent message: " + data.getName());
            }
        });
    }
}
