package com.molim.springboot.cruddemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molim.springboot.cruddemo.dao.EmployeeDAO;
import com.molim.springboot.cruddemo.entity.Employee;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeDAO employeeDAO;
	
	
	@GetMapping()
	private List<Employee> getEmployees() {
		return employeeDAO.findAll();
	}
	
	@GetMapping("/{id}")
	private Employee getEmployee(@PathVariable() int id) {
		return employeeDAO.findById(id);
	}
	
}
