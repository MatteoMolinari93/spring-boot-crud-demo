package com.molim.springboot.cruddemo.dao;

import java.util.List;

import com.molim.springboot.cruddemo.entity.Employee;

public interface EmployeeDAO {
	
	public List<Employee> findAll();

}
