package com.attornatus.peoplemanager.dto;

import java.util.ArrayList;
import java.util.List;

import com.attornatus.peoplemanager.entity.PersonAddress;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonAddressResponseDTO {

	private Long id;
	
	private String address;
	
	private String cep;
	
	private String number;
	
	private String mainStr;

	private String city;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
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

	public String getMainStr() {
		return mainStr;
	}

	public void setMainStr(String main) {
		this.mainStr = main;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public static PersonAddressResponseDTO convertToDTO(PersonAddress personAddress) {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		Integer main = personAddress.getMain();
		String mainStr = main.equals(1) ? "Yes" : "No";
		
		PersonAddressResponseDTO personAddressDTO = objMapper.convertValue(personAddress, PersonAddressResponseDTO.class); 
		personAddressDTO.setMainStr(mainStr);
		
		return personAddressDTO;
	}

	public static List<PersonAddressResponseDTO> convertToDTO(List<PersonAddress> personAddressList) {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		List<PersonAddressResponseDTO> personAddressDTOList = new ArrayList<PersonAddressResponseDTO>();
		for (PersonAddress personAddress : personAddressList) {
			Integer main = personAddress.getMain();
			String mainStr = main.equals(1) ? "Yes" : "No";
			
			PersonAddressResponseDTO personAddressDTO = objMapper.convertValue(personAddress, PersonAddressResponseDTO.class);
			personAddressDTO.setMainStr(mainStr);
			
			personAddressDTOList.add(personAddressDTO);
		}
		
		return personAddressDTOList;
	}
	
}
