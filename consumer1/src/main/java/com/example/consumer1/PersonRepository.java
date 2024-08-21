package com.example.consumer1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
	static Person named(String name) {
		Person person = new Person();
		person.setName(name);
		return person;
	}
}