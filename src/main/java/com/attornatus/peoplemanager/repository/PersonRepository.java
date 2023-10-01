package com.attornatus.peoplemanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attornatus.peoplemanager.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	Optional<Person> findById(Long id);
	
}
