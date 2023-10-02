package com.attornatus.peoplemanager.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.attornatus.peoplemanager.entity.PersonAddress;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonAddressRequestDTO {

	@NotNull(message = "Address must be defined")
	@NotEmpty(message = "Address cannot be empty")
	private String address;
	
	private String cep;

	private String number;
	
	@Min(value = 0, message = "Main must be 1 to true or 0 to false")
	@Max(value = 1, message = "Main must be 1 to true and 0 to false")
	@NotNull(message = "Main must be 1 to true or 0 to false")
	private Integer main;

	private String city;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getMain() {
		return main;
	}

	public void setMain(Integer main) {
		this.main = main;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public PersonAddress convertToEntity() {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		PersonAddress personAddress = objMapper.convertValue(this, PersonAddress.class);
		return personAddress;
	}
	
}
