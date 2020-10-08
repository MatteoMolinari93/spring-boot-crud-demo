package com.molim.springboot.cruddemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.molim.springboot.cruddemo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
