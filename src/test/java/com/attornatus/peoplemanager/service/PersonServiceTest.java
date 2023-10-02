package com.attornatus.peoplemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.attornatus.peoplemanager.dto.PersonRequestDTO;
import com.attornatus.peoplemanager.entity.Person;
import com.attornatus.peoplemanager.exception.WarningException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PersonServiceTest {

	@Autowired
	private PersonService personService;
	
	@Test
	void shouldntUpdateNonExistingPerson() {
		PersonRequestDTO personDTO = new PersonRequestDTO();
		personDTO.setName("Bob");
		personDTO.setBirthDateStr("02/10/2017");
		
		try {
			this.personService.updatePerson(-60L, personDTO);
			
			fail("Shouldn't update a non-existing person");
		} catch (Exception e) {
			assertEquals(WarningException.class, e.getClass());
		}
	}
	
	@Test
	void shouldUpdatePerson() throws ParseException {
		PersonRequestDTO personDTO = new PersonRequestDTO();
		personDTO.setName("Bob");
		personDTO.setBirthDateStr("02/10/2017");

		Person person = this.personService.createPerson(personDTO);
		
		Long personId = person.getId();
		
		PersonRequestDTO newPersonDTO = new PersonRequestDTO();
		newPersonDTO.setName("Bob Mod");
		newPersonDTO.setBirthDateStr("02/10/2012");
		
		Person newPerson = this.personService.updatePerson(personId, newPersonDTO);
		
		assertNotEquals(person.getName(), newPerson.getName());
		assertNotEquals(person.getBirthDate(), newPerson.getBirthDate());
	}

	@Test
	void shouldReturnPersonWithStartNameC() {
		List<Person> personList = this.personService.findPersonsByNameLike("C");
		for (Person person : personList) {
			String name = person.getName();
			
			if (name.startsWith("C") == false) {
				fail("Should have brought people with names that start with C");
			}
		}
	}
	
}
