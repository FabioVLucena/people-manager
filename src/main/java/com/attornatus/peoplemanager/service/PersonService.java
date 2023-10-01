package com.attornatus.peoplemanager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attornatus.peoplemanager.dto.PersonRequestDTO;
import com.attornatus.peoplemanager.entity.Person;
import com.attornatus.peoplemanager.exception.WarningException;
import com.attornatus.peoplemanager.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PersonService {

	private ObjectMapper objMapper;
	private PersonRepository personRepository;
	
	public PersonService(PersonRepository peopleRepository, ObjectMapper objMapper) {
		this.objMapper = objMapper;
		this.personRepository = peopleRepository;
	}

	@Transactional(readOnly = true)
	public Person getPersonById(Long id) throws Exception {
		Optional<Person> optPerson = this.personRepository.findById(id);
		
		Person person = optPerson.orElseThrow(() -> new WarningException("Person not found"));
		
		return person;
	}
	
	@Transactional(readOnly = true)
	public List<Person> findAllPersons() {
		List<Person> personList = this.personRepository.findAll();
		
		return personList;
	}
	
	@Transactional(readOnly = false)
	public Person createPerson(PersonRequestDTO personDTO) throws ParseException {
		Person person = this.objMapper.convertValue(personDTO, Person.class);

		String birthDateStr = personDTO.getBirthDateStr();
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date birthDate = dataFormat.parse(birthDateStr);
		
		person.setBirthDate(birthDate);
		
		this.personRepository.save(person);	
		
		return person;
	}
	
	@Transactional(readOnly = false)
	public Person updatePerson(Long id, PersonRequestDTO personDTO) throws Exception {
		// VALIDA SE A PESSOA EXISTE
		getPersonById(id);
		
		Person person = this.objMapper.convertValue(personDTO, Person.class);
		person.setId(id);
		
		String birthDateStr = personDTO.getBirthDateStr();
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date birthDate = dataFormat.parse(birthDateStr);
		
		person.setBirthDate(birthDate);
		
		this.personRepository.save(person);
		
		return person;
	}
	
}
