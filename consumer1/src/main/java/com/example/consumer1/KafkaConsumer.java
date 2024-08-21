package com.example.consumer1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

import com.example.consumer1.multitenancy.TenantIdentifierResolver;

@Component
public class KafkaConsumer {
    @Autowired
    private Service1 service1;

    @Autowired
    private Service2 service2;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TenantIdentifierResolver tenantIdentifierResolver;

    @RetryableTopic(attempts = "2") // 1 retry
    @KafkaListener(topics = "message", groupId = "consumer1")
    public void consumeMessage(String message) throws Exception {
        String tenant = Math.random() < 0.5 ? "tenant1" : "tenant2";
        tenantIdentifierResolver.setCurrentTenant(tenant);
        System.out.println("Received message: " + message);

        try {
            processMessage(message);
        } finally {
            tenantIdentifierResolver.setCurrentTenant("public");
        }
    }

    private void processMessage(String message) throws Exception {
        if (message.equals("error1") || message.equals("error")) {
            throw new Exception("Error processing message");
        }

        Person person1 = new Person();
        person1.setName(message + "1");
        personRepository.save(person1);
        System.out.println("Processed message 1: " + message);

        Person person2 = new Person();
        person2.setName(message + "2");
        personRepository.save(person2);
        System.out.println("Processed message 2: " + message);
    }
}
