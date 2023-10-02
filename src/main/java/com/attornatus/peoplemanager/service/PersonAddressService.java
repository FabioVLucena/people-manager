package com.attornatus.peoplemanager.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attornatus.peoplemanager.dto.PersonAddressRequestDTO;
import com.attornatus.peoplemanager.entity.Person;
import com.attornatus.peoplemanager.entity.PersonAddress;
import com.attornatus.peoplemanager.enums.MainEnum;
import com.attornatus.peoplemanager.exception.WarningException;
import com.attornatus.peoplemanager.repository.PersonAddressRepository;

@Service
public class PersonAddressService {

	private PersonService personService;
	private PersonAddressRepository personAddressRepository;

	public PersonAddressService(PersonService personService, PersonAddressRepository personAddressRepository) {
		this.personService = personService;
		this.personAddressRepository = personAddressRepository;
	}
	
	@Transactional(readOnly = true)
	public PersonAddress getPersonAddressById(Long id) {
		Optional<PersonAddress> optPersonAddress = this.personAddressRepository.findById(id); 
		
		PersonAddress personAddress = optPersonAddress.orElseThrow(() -> new WarningException("Person address not found"));
		
		return personAddress;
	}
	
	@Transactional(readOnly = false)
	public PersonAddress createPersonAddress(Long personId, PersonAddressRequestDTO personAddressDTO) throws WarningException {
		Person person = this.personService.getPersonById(personId);
		
		PersonAddress personAddress = personAddressDTO.convertToEntity();
		personAddress.setPerson(person);
		
		Integer main = personAddress.getMain();
		if (main.equals(MainEnum.YES)) changeOldMainAddress(personId);
		
		this.personAddressRepository.save(personAddress);

		return personAddress;
	}
	
	@Transactional(readOnly = true)
	public List<PersonAddress> findAllPersonAddressByPersonId(Long personId) {
		this.personService.getPersonById(personId);
		
		List<PersonAddress> personAddressList = 
				this.personAddressRepository.findAllPersonAddressesByPersonId(personId);
		
		return personAddressList;
	}
	
	@Transactional(readOnly = true)
	public PersonAddress getMainPersonAddressByPersonId(Long personId) {
		PersonAddress personAddress = this.personAddressRepository.getMainPersonAddressesByPersonId(personId);
	
		if (personAddress == null) throw new WarningException("Main address not found");
		
		return personAddress;
	}
	
	@Transactional(readOnly = false)
	public PersonAddress setMainPersonAdress(Long id) {
		PersonAddress newMainPersonAddress = getPersonAddressById(id);
		Long personId = newMainPersonAddress.getPerson().getId();
		
		this.personService.getPersonById(personId);

		changeOldMainAddress(personId);

		newMainPersonAddress.setMain(MainEnum.YES);

		this.personAddressRepository.save(newMainPersonAddress);
		
		return newMainPersonAddress;
	}

	@Transactional(readOnly = false)
	private void changeOldMainAddress(Long personId) {
		try {
			PersonAddress oldMainPersonAddress = getMainPersonAddressByPersonId(personId);
			oldMainPersonAddress.setMain(MainEnum.NO);
			
			this.personAddressRepository.save(oldMainPersonAddress);
		} catch (WarningException e) { }
	}
}
