package com.attornatus.peoplemanager.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.peoplemanager.dto.PersonRequestDTO;
import com.attornatus.peoplemanager.entity.Person;
import com.attornatus.peoplemanager.service.PersonService;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

	private PersonService personService;
	
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable Long id) throws Exception {
		Person person = this.personService.getPersonById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}
	
	@GetMapping
	public ResponseEntity<List<Person>> findAllPersons() {
		List<Person> personList = this.personService.findAllPersons();
		
		return ResponseEntity.status(HttpStatus.OK).body(personList);
	}
	
	@PostMapping
	public ResponseEntity<Person> createPerson(@RequestBody @Valid PersonRequestDTO personDTO) throws ParseException {
		Person person = this.personService.createPerson(personDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(person);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody @Valid PersonRequestDTO personDTO) throws Exception {
		Person person = this.personService.updatePerson(id, personDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}
	
}
