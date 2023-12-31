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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.peoplemanager.dto.PersonAddressRequestDTO;
import com.attornatus.peoplemanager.dto.PersonAddressResponseDTO;
import com.attornatus.peoplemanager.dto.PersonRequestDTO;
import com.attornatus.peoplemanager.entity.Person;
import com.attornatus.peoplemanager.entity.PersonAddress;
import com.attornatus.peoplemanager.exception.WarningException;
import com.attornatus.peoplemanager.service.PersonAddressService;
import com.attornatus.peoplemanager.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

	private PersonService personService;
	
	private PersonAddressService personAddressService; 
	
	public PersonController(PersonService personService, PersonAddressService personAddressService) {
		this.personService = personService;
		this.personAddressService = personAddressService;
	}
	
	@Operation(summary = "Get a person by its id")
	@GetMapping("/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable Long id) throws WarningException {
		Person person = this.personService.getPersonById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}
	
	@Operation(summary = "Get the list of all persons")
	@GetMapping
	public ResponseEntity<List<Person>> findAllPersons() {
		List<Person> personList = this.personService.findAllPersons();
		
		return ResponseEntity.status(HttpStatus.OK).body(personList);
	}
	
	@Operation(summary = "Get the list of all persons with name like search")
	@GetMapping("/search")
	public ResponseEntity<List<Person>> findPersonsByNameLike(@RequestParam String name) {
		List<Person> personList = this.personService.findPersonsByNameLike(name);
		
		return ResponseEntity.status(HttpStatus.OK).body(personList);
	}
	
	@Operation(summary = "Creates a new person")
	@PostMapping
	public ResponseEntity<Person> createPerson(@RequestBody @Valid PersonRequestDTO personDTO) throws ParseException {
		Person person = this.personService.createPerson(personDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(person);
	}
	
	@Operation(summary = "Update a person's data")
	@PutMapping("/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody @Valid PersonRequestDTO personDTO) throws WarningException, ParseException {
		Person person = this.personService.updatePerson(id, personDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}
	
	@Operation(summary = "Creates a new person address")
	@PostMapping("/{id}/addresses")
	public ResponseEntity<PersonAddressResponseDTO> createPersonAddress(@PathVariable Long id, @RequestBody @Valid PersonAddressRequestDTO personAddressDTO) {
		PersonAddress personAddress = this.personAddressService.createPersonAddress(id, personAddressDTO);
		
		PersonAddressResponseDTO responseDTO = PersonAddressResponseDTO.convertToDTO(personAddress);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}
	
	@Operation(summary = "Get a list of all a person's addresses")
	@GetMapping("/{id}/addresses")
	public ResponseEntity<List<PersonAddressResponseDTO>> findAllPersonAddressByPersonId(@PathVariable Long id) {
		List<PersonAddress> personAddressList = 
				this.personAddressService.findAllPersonAddressByPersonId(id);
		
		List<PersonAddressResponseDTO> responseDTO = PersonAddressResponseDTO.convertToDTO(personAddressList);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@Operation(summary = "Get the person's main address")
	@GetMapping("/{id}/addresses/main")
	public ResponseEntity<PersonAddressResponseDTO> getMainPersonAddress(@PathVariable Long id) {
		PersonAddress personAddress = this.personAddressService.getMainPersonAddressByPersonId(id);
		
		PersonAddressResponseDTO responseDTO = PersonAddressResponseDTO.convertToDTO(personAddress);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@Operation(summary = "Change the person's main address")
	@PutMapping("/{id}/addresses/{detailid}/main")
	public ResponseEntity<PersonAddressResponseDTO> setMainPersonAddress(@PathVariable Long detailid) {
		PersonAddress personAddress = this.personAddressService.setMainPersonAdress(detailid);
		
		PersonAddressResponseDTO responseDTO = PersonAddressResponseDTO.convertToDTO(personAddress);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
}
