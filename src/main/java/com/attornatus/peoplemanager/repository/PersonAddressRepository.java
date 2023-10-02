package com.attornatus.peoplemanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.attornatus.peoplemanager.entity.PersonAddress;

@Repository
public interface PersonAddressRepository extends JpaRepository<PersonAddress, Long> {

	Optional<PersonAddress> findById(Long id);
	
	@Query("SELECT pedd FROM person_address pedd WHERE pedd.person.id = :personId ORDER BY pedd.main DESC")
	List<PersonAddress> findAllPersonAddressesByPersonId(@Param("personId") Long personId);

	@Query("SELECT pedd FROM person_address pedd WHERE pedd.person.id = :personId AND pedd.main = 1")
	PersonAddress getMainPersonAddressesByPersonId(@Param("personId") Long personId);
	
}
