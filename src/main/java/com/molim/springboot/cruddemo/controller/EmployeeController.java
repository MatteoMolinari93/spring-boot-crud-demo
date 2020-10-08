package com.molim.springboot.cruddemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping()
	private void newEmployee(@RequestBody Employee employee) {
		employee.setId(0);
		employeeService.save(employee);
	}
	
	@PutMapping("/{id}")
	private void updateEmployee(@RequestBody Employee employee) {
		employeeService.save(employee);
	}
	
	@DeleteMapping("/{id}")
	private void deleteEmployee(@PathVariable() int id) {
		employeeService.deleteById(id);
	}
	
	
}
