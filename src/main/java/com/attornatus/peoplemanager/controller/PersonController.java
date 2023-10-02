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
import com.attornatus.peoplemanager.entity.PersonAddress;
import com.attornatus.peoplemanager.exception.WarningException;
import com.attornatus.peoplemanager.service.PersonAddressService;
import com.attornatus.peoplemanager.service.PersonService;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

	private PersonService personService;
	
	private PersonAddressService personAddressService; 
	
	public PersonController(PersonService personService, PersonAddressService personAddressService) {
		this.personService = personService;
		this.personAddressService = personAddressService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable Long id) throws WarningException {
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
	public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody @Valid PersonRequestDTO personDTO) throws WarningException, ParseException {
		Person person = this.personService.updatePerson(id, personDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}
	
	@PostMapping("/{id}/addresses")
	public ResponseEntity<PersonAddress> createPersonAddress(@PathVariable Long id, @RequestBody PersonAddress personAddress) {
		this.personAddressService.createPersonAddress(id, personAddress);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(personAddress);
	}
	
	@GetMapping("/{id}/addresses")
	public ResponseEntity<List<PersonAddress>> findAllPersonAddressByPersonId(@PathVariable Long id) {
		List<PersonAddress> personAddressList = 
				this.personAddressService.findAllPersonAddressByPersonId(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(personAddressList);
	}
	
	@GetMapping("/{id}/addresses/main")
	public ResponseEntity<PersonAddress> getMainPersonAddress(@PathVariable Long id) {
		PersonAddress personAddress = this.personAddressService.getMainPersonAddressByPersonId(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(personAddress);
	}
	
	@PutMapping("/{id}/addresses/{detailid}/main")
	public ResponseEntity<PersonAddress> setMainPersonAddress(@PathVariable Long detailid) {
		PersonAddress personAddress = this.personAddressService.setMainPersonAdress(detailid);
		
		return ResponseEntity.status(HttpStatus.OK).body(personAddress);
	}
	
}
