package com.attornatus.peoplemanager.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attornatus.peoplemanager.dto.PersonRequestDTO;
import com.attornatus.peoplemanager.entity.Person;
import com.attornatus.peoplemanager.exception.WarningException;
import com.attornatus.peoplemanager.repository.PersonRepository;

@Service
public class PersonService {

	private PersonRepository personRepository;
	
	public PersonService(PersonRepository peopleRepository) {
		this.personRepository = peopleRepository;
	}

	@Transactional(readOnly = true)
	public Person getPersonById(Long id) throws WarningException {
		Optional<Person> optPerson = this.personRepository.findById(id);
		
		Person person = optPerson.orElseThrow(() -> new WarningException("Person not found"));
		
		return person;
	}
	
	@Transactional(readOnly = true)
	public List<Person> findAllPersons() {
		List<Person> personList = this.personRepository.findAll();
		
		return personList;
	}
	
	@Transactional(readOnly = true)
	public List<Person> findPersonsByNameLike(String name) {
		List<Person> personList = this.personRepository.findByNameLike(name);
		
		return personList;
	}
	
	@Transactional(readOnly = false)
	public Person createPerson(PersonRequestDTO personDTO) throws ParseException {
		Person person = personDTO.convertToEntity();
		
		this.personRepository.save(person);	
		
		return person;
	}
	
	@Transactional(readOnly = false)
	public Person updatePerson(Long id, PersonRequestDTO personDTO) throws WarningException, ParseException {
		getPersonById(id);
		
		Person person = personDTO.convertToEntity();
		person.setId(id);
		
		this.personRepository.save(person);
		
		return person;
	}
	
}
