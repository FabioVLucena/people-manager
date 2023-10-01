package com.attornatus.peoplemanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attornatus.peoplemanager.entity.Person;
import com.attornatus.peoplemanager.repository.PersonRepository;

@Service
public class PersonService {

	private PersonRepository personRepository;
	
	public PersonService(PersonRepository peopleRepository) {
		this.personRepository = peopleRepository;
	}

	@Transactional(readOnly = true)
	public Person getPersonById(Long id) throws Exception {
		Optional<Person> optPerson = this.personRepository.findById(id);
		
		Person person = optPerson.orElseThrow(() -> new Exception("NotFound"));
		
		return person;
	}
	
	@Transactional(readOnly = true)
	public List<Person> findAllPersons() {
		List<Person> personList = this.personRepository.findAll();
		
		return personList;
	}
	
	@Transactional(readOnly = false)
	public Person createPerson(Person person) {
		this.personRepository.save(person);	
		
		return person;
	}
	
	@Transactional(readOnly = false)
	public Person updatePerson(Long id, Person person) throws Exception {
		Person oldPerson = getPersonById(id);
		
		oldPerson.setName(person.getName());
		
		this.personRepository.save(oldPerson);
		
		return oldPerson;
	}
	
}
