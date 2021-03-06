package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.entity.Address;
import com.example.entity.Student;
import com.example.repository.AddressRepository;
import com.example.repository.StudentRepository;
import com.example.request.CreateStudentRequest;
import com.example.request.InQueryRequest;
import com.example.request.UpdateStudentRequest;


// Service Layer calls methods defined in Repository

@Service
public class StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	public List<Student> getAllStudents () {
		return studentRepository.findAll();
	}
	
	public List<Student> getByFirstName (String firstName) {
		return studentRepository.findByFirstName(firstName);
	}
	
	public Student getByFirstNameAndLastName (String firstName, String lastName) {
//		return studentRepository.findByFirstNameAndLastName(firstName, lastName); JPA method
		return studentRepository.getByFirstNameAndLastName(firstName, lastName);
	}
	
	public List<Student> getByFirstNameOrLastName (String firstName, String lastName) {
		return studentRepository.findByFirstNameOrLastName(firstName, lastName);
	}
	
	public List<Student> getByFirstNameIn (InQueryRequest inQueryRequest) {
		return studentRepository.findByFirstNameIn(inQueryRequest.getFirstNames());
	}
	
	public List<Student> getAllStudentsWithPagination (int pageNo, int pageSize) {
		PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
		
		return studentRepository.findAll(pageable).getContent();
	}
	
	public List<Student> getAllStudentsWithSorting () {
		Sort sort = Sort.by(Sort.Direction.ASC, "firstName", "lastName", "email");
		
		return studentRepository.findAll(sort);
	}
	
	public List<Student> like(String firstName) {
		return studentRepository.findByFirstNameContains(firstName);
	}
	
	public List<Student> startsWith(String firstName) {
		return studentRepository.findByFirstNameStartsWith(firstName);
	}
	
	public List<Student> endsWith(String firstName) {
		return studentRepository.findByFirstNameEndsWith(firstName);
	}
	
	public Student createStudent (CreateStudentRequest createStudentRequest) {
		Student student = new Student(createStudentRequest);
		
		Address address = new Address();
		address.setStreet(createStudentRequest.getStreet());
		address.setCity(createStudentRequest.getCity());
		
		addressRepository.save(address);
		
		student.setAddress(address);
		student = studentRepository.save(student);
		return student;
	}
	
	//update conditional branching to switch
	public Student updateStudent (UpdateStudentRequest updateStudentRequest) {
		Student student = studentRepository.findById(updateStudentRequest.getId()).get();
		
		if (updateStudentRequest.getFirstName() != null && !updateStudentRequest.getFirstName().isEmpty()) {
			student.setFirstName(updateStudentRequest.getFirstName());
		}
		
		if (updateStudentRequest.getLastName() != null && !updateStudentRequest.getLastName().isEmpty()) {
			student.setLastName(updateStudentRequest.getLastName());
		}
		
		if (updateStudentRequest.getEmail() != null && !updateStudentRequest.getEmail().isEmpty()) {
			student.setEmail(updateStudentRequest.getEmail());
		}
		
		student = studentRepository.save(student);
		return student;
	}
	
//	public Integer updateStudentWithJpql (Long id, String firstName) {
//		return studentRepository.updateFirstName(id, firstName);
//	}
	
	public String deleteStudent (long id) {
		studentRepository.deleteById(id);
		return "Student has been deleted successfuly";
	}
}
