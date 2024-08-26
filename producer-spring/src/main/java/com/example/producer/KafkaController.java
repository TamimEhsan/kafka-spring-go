package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class KafkaController {
    @Autowired
    private KafkaSender kafkaSender;


    @PostMapping("/produce")
    public ResponseEntity<String> produce(@RequestBody PersonDto message) {
        System.out.println("Person DTO:: "+message.getName());
        kafkaSender.send("message",message);
        
        return ResponseEntity.ok("Message sent to the Kafka Topic java_in_use_topic Successfully");
    }

    @PostMapping("/email")
    public ResponseEntity<String> produceEmail(@RequestBody EmailDto message) {
        System.out.println("Email DTO:: "+message.getTo());
        kafkaSender.send("email",message);
        
        return ResponseEntity.ok("Message sent to the Kafka Topic java_in_use_topic Successfully");
    }
}
