package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/")
public class StudentController {
	
	@GetMapping("/get")
//	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getStudent () {
		return "Hello Student";
	}
}
