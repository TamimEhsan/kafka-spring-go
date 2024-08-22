package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.common.EmailDto;
import com.example.common.PersonDto;

@Controller
public class KafkaController {
    @Autowired
    private PersonProducerService producerService;

    @Autowired 
    private EmailProducerService producerService2;

    @PostMapping("/produce")
    public ResponseEntity<String> produce(@RequestBody PersonDto message) {
        System.out.println("Person DTO:: "+message.getName());
        producerService.produce(message);
        
        return ResponseEntity.ok("Message sent to the Kafka Topic java_in_use_topic Successfully");
    }

    @PostMapping("/email")
    public ResponseEntity<String> produceEmail(@RequestBody EmailDto message) {
        System.out.println("Email DTO:: "+message.getTo());
        producerService2.produce(message);
        
        return ResponseEntity.ok("Message sent to the Kafka Topic java_in_use_topic Successfully");
    }
}
