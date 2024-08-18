package com.example.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
/**
 * Class to interact with Kafka producer
 * Singleton class by design
 */
public class ProducerUtils {

    
    // @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers = "localhost:29092";

    private Properties producerProperties;

    private KafkaProducer<String, String> producer; 

    private static ProducerUtils instance;



    private ProducerUtils() {
       
        producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", bootstrapServers);
        producerProperties.put("acks", "all");
        producerProperties.put("retries", 0);
        producerProperties.put("batch.size", 16384);
        producerProperties.put("linger.ms", 1);
        producerProperties.put("buffer.memory", 33554432);
        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        producer = new KafkaProducer<>(producerProperties);
    }

    /**
     * Function to get the instance of the class
     * @return
     */
    public static ProducerUtils getInstance() {
        if (instance == null) {
            instance = new ProducerUtils();
        }
        return instance;
    }

    /**
     * Function to get the producer object
     * @return
     */
    public KafkaProducer<String, String> getProducer() {
        return producer;
    }

}