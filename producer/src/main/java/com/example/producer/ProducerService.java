package com.example.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    // kafka producer to topic message
    public void produce(String message) {
        KafkaProducer<String, String> producer = ProducerUtils.getInstance().getProducer();
        producer.send(new ProducerRecord<String, String>("message", message));
    }
}
