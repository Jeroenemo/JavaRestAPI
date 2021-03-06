package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
		
//		Method Proxy from JPA
		List<Student> findByFirstName(String firstName);
		
		Student findByFirstNameAndLastName (String firstName, String lastName);
		
		List<Student> findByFirstNameOrLastName (String firstName, String lastName);
		
		List<Student> findByFirstNameIn (List<String> firstNames);
		
		List<Student> findByFirstNameContains(String firstName);
		
		List<Student> findByFirstNameStartsWith(String firstName);
		
		List<Student> findByFirstNameEndsWith(String firstName);
		
		
//		JPQL
		@Query("From Student where firstName = :first_name and lastName = :lastName")
		Student getByFirstNameAndLastName (@Param("first_name") String firstName, String lastName);
		
//		@Modifying
//		@Transactional
//		@Query("Update Student set firstName = :firstName where id = :id")
//		Integer updateFirstName (Long id, String fistName);
}
