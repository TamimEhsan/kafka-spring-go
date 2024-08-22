package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.common.EmailDto;

@Service
public class EmailProducerService {

    @Autowired
    private KafkaTemplate<String, EmailDto> kafkaTemplate;

    // kafka producer to topic message
    public void produce(EmailDto data) {
        Message<EmailDto> message = MessageBuilder.withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "email")
                .build();
        kafkaTemplate.send(message);
    }
}
