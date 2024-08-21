package com.example.consumer1;

import java.util.List;

import org.hibernate.engine.internal.Collections;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;
    
    @GetMapping("/person")
    public List<Person> getPerson() {
        System.out.println("Getting all persons");
        List<Person> persons = personRepository.findAll();
        System.out.println("Got all persons");
        long cnt = personRepository.count();
        System.out.println("Counted all persons: " + cnt);
        return persons;
    }
}
