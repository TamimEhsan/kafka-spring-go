package com.example.consumer1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service1 {
    @Autowired
    private PersonRepository personRepository;

    public void savePerson(String name) {
        Person person = new Person();
        person.setName(name);
        personRepository.save(person);
    }
    
}
