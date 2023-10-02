package com.attornatus.peoplemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.attornatus.peoplemanager.dto.PersonAddressRequestDTO;
import com.attornatus.peoplemanager.entity.PersonAddress;
import com.attornatus.peoplemanager.enums.MainEnum;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PersonAddressServiceTest {
	
	@Autowired
	private PersonAddressService personAddressService;
	
	@Test
	void shouldFindAllPersonAddress() {	
		Long personId = 1L;
		Integer expectedSize = 3;
		
		List<PersonAddress> personAddressList = 
				this.personAddressService.findAllPersonAddressByPersonId(personId);
		
		assertEquals(expectedSize, personAddressList.size());

		for (PersonAddress personAddress : personAddressList) {
			assertEquals(personId, personAddress.getPerson().getId());
		}
	}

	@Test
	void shouldFindMainPersonAddress() {
		Long personId = 1L;
		Integer expectedMain = MainEnum.YES;
		
		PersonAddress personAddress = 
				this.personAddressService.getMainPersonAddressByPersonId(personId);
		
		assertEquals(personId, personAddress.getPerson().getId());
		assertEquals(expectedMain, personAddress.getMain());
	}

	@Test
	void shouldOnlyBeOneMainAddress() { 
		Long personId = 2L;
		
		PersonAddressRequestDTO personAddressDTO = new PersonAddressRequestDTO();
		personAddressDTO.setAddress("Address Test");
		personAddressDTO.setCep("65851");
		personAddressDTO.setMain(MainEnum.YES);
		personAddressDTO.setNumber("N/A");
		personAddressDTO.setCity("City Test");
		
		this.personAddressService.createPersonAddress(personId, personAddressDTO);
		this.personAddressService.createPersonAddress(personId, personAddressDTO);
		this.personAddressService.createPersonAddress(personId, personAddressDTO);
		
		this.personAddressService.getMainPersonAddressByPersonId(personId);
	}

	@Test
	void shouldChangedMainAddress() { 
		Long personId = 1L;
		
		PersonAddress oldMainPersonAddress = this.personAddressService.getMainPersonAddressByPersonId(personId);
		
		List<PersonAddress> personAddressList = this.personAddressService.findAllPersonAddressByPersonId(personId);
		PersonAddress newMainPersonAddress = personAddressList.get(2);
		
		this.personAddressService.setMainPersonAdress(newMainPersonAddress.getId());
		
		newMainPersonAddress = this.personAddressService.getMainPersonAddressByPersonId(personId);
		
		assertEquals(oldMainPersonAddress.getMain(), newMainPersonAddress.getMain());
		assertEquals(oldMainPersonAddress.getPerson().getId(), newMainPersonAddress.getPerson().getId());

		assertNotEquals(oldMainPersonAddress.getId(), newMainPersonAddress.getId());
		assertNotEquals(oldMainPersonAddress.getAddress(), newMainPersonAddress.getAddress());
		assertNotEquals(oldMainPersonAddress.getCep(), newMainPersonAddress.getCep());
		assertNotEquals(oldMainPersonAddress.getCity(), newMainPersonAddress.getCity());
		assertNotEquals(oldMainPersonAddress.getNumber(), newMainPersonAddress.getNumber());
	}
	
}
