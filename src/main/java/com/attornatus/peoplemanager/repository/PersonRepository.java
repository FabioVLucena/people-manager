package com.attornatus.peoplemanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.attornatus.peoplemanager.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	Optional<Person> findById(Long id);
	
	@Query("SELECT p FROM person p WHERE lower(p.name) like lower(concat('%', :name,'%'))")
	List<Person> findByNameLike(@Param("name") String name);
	
}
