package com.attornatus.peoplemanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "person_address")
public class PersonAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person person;

	@Column(name = "address", nullable = false, unique = false)
	private String address;
	
	@Column(name = "cep", nullable = true, unique = false)
	private String cep;
	
	@Column(name = "main", length = 1, nullable = false, unique = false)
	private Integer main;
	
	@Column(name = "number", nullable = true, unique = false)
	private String number;

	// IA SER LEGAL SE ISSO FOSSE UMA TABELA
	@Column(name = "city", nullable = false, unique = false)
	private String city;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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

	public Integer getMain() {
		return main;
	}

	public void setMain(Integer main) {
		this.main = main;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
