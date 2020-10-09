package com.molim.springboot.cruddemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private CollectionModel<Employee> getEmployees() {
		List<Employee> employees = employeeService.findAll();
		for(Employee employee : employees) {
			addEmployeeLinks(employee);
		}
		Link link = WebMvcLinkBuilder.linkTo(EmployeeController.class).withSelfRel();
		return CollectionModel.of(employees, link);
	}
	
	@GetMapping("/{id}")
	private Employee getEmployee(@PathVariable() int id) {
		Employee employee = employeeService.findById(id);
		return addEmployeeLinks(employee);
	}
	
	@PostMapping()
	private ResponseEntity<Employee> newEmployee(@RequestBody Employee employee) {
		employee.setId(0);
		Employee newEmployee = employeeService.save(employee);
		return new ResponseEntity<Employee>(addEmployeeLinks(newEmployee), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	private void updateEmployee(@PathVariable() int id, @RequestBody Employee employee) {
		employee.setId(id);
		employeeService.save(employee);
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<Employee> deleteEmployee(@PathVariable() int id) {
		employeeService.deleteById(id);
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	}
	
	private Employee addEmployeeLinks(Employee employee) {
		return employee.add(WebMvcLinkBuilder.linkTo(EmployeeController.class).slash(employee.getId()).withSelfRel());
	}
	
	
}
