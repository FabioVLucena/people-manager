package com.attornatus.peoplemanager.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.attornatus.peoplemanager.entity.Person;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonRequestDTO {

	@NotNull(message = "Invalid name")
	@Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+([ '-][A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "Invalid name")
	private String name;
	
	@NotNull(message = "Invalid birth date. Formats accepted mm/dd/yyyy")
	@Pattern(regexp = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)", 
			 message = "Invalid birth date. Formats accepted mm/dd/yyyy")
	private String birthDateStr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDateStr() {
		return birthDateStr;
	}

	public void setBirthDateStr(String birthDate) {
		this.birthDateStr = birthDate;
	}
	
	public Person convertToEntity() throws ParseException {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		Person person = objMapper.convertValue(this, Person.class);

		String birthDateStr = this.getBirthDateStr();
		
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date birthDate = dataFormat.parse(birthDateStr);
		
		person.setBirthDate(birthDate);
		
		return person;
	}
	
}
