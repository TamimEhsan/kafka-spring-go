package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class KafkaController {
    @Autowired
    private ProducerService producerService;

    @PostMapping("/produce")
    public ResponseEntity<String> produce(@RequestBody String message) {
        producerService.produce(message);
        System.out.println(message);
        return ResponseEntity.ok("Message sent to the Kafka Topic java_in_use_topic Successfully");
    }
}
