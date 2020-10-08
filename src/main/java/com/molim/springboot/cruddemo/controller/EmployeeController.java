package com.molim.springboot.cruddemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molim.springboot.cruddemo.entity.Employee;
import com.molim.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping()
	private List<Employee> getEmployees() {
		return employeeService.findAll();
	}
	
	@GetMapping("/{id}")
	private Employee getEmployee(@PathVariable() int id) {
		return employeeService.findById(id);
	}
	
}
