package com.molim.springboot.cruddemo.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.molim.springboot.cruddemo.entity.Employee;
import com.molim.springboot.cruddemo.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {
	
	@Autowired 
	private MockMvc mvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	@Test
	public void getListShouldFetchAHalDocument() throws Exception {

		given(employeeService.findAll()).willReturn(
			Arrays.asList(
				new Employee(1,"Frodo", "Baggins", "frodo@test.com"),
				new Employee(2,"Bilbo", "Baggins", "bilbo@test.com")));

		mvc.perform(get("/employees").accept(MediaTypes.HAL_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
			.andExpect(checkIfHasLinksField())
			.andExpect(jsonPath("$._embedded.employees[0].id", is(1)))
			.andExpect(jsonPath("$._embedded.employees[0].firstName", is("Frodo")))
			.andExpect(jsonPath("$._embedded.employees[0].lastName", is("Baggins")))
			.andExpect(jsonPath("$._embedded.employees[0].email", is("frodo@test.com")));
	}
	
	@Test
	public void getResourceShouldFetchAHalDocument() throws Exception {

		given(employeeService.findById(1)).willReturn(
				new Employee(1,"Frodo", "Baggins", "frodo@email.com"));

		mvc.perform(get("/employees/1").accept(MediaTypes.HAL_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
			.andExpect(checkIfHasLinksField())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.firstName", is("Frodo")))
			.andExpect(jsonPath("$.lastName", is("Baggins")))
			.andExpect(jsonPath("$.email", is("frodo@email.com")));
	}
	
	@Test
	public void postShouldFetchANewHalDocument() throws Exception {
		given(employeeService.save(new Employee("Leslie", "Andrews", "leslie@test.com"))).willReturn(new Employee(1, "Leslie", "Andrews", "leslie@test.com"));
		
		mvc.perform(post("/employees").accept(MediaTypes.HAL_JSON_VALUE).content("{"
				+ "\"firstName\": \"Leslie\","
				+ "\"lastName\": \"Andrews\","
				+ "\"email\": \"leslie@test.com\""
				+ "}").contentType(MediaTypes.HAL_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
			.andExpect(checkIfHasLinksField())
			.andExpect(jsonPath("$.id", notNullValue()));
	}
	
	@Test
	public void putShouldFetchAHalDocument() throws Exception {
		given(employeeService.save(new Employee(1, "Leslie", "Andrews", "leslie@test.com"))).willReturn(new Employee(1, "Leslie", "Andrews", "leslie@test.com"));
		
		mvc.perform(put("/employees/1").accept(MediaTypes.HAL_JSON_VALUE).content("{"
				+ "\"id\": \"1\","
				+ "\"firstName\": \"Leslie\","
				+ "\"lastName\": \"Andrews\","
				+ "\"email\": \"leslie@test.com\""
				+ "}").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteShouldFetchNoDocument() throws Exception {		
		mvc.perform(delete("/employees/1"))
			.andExpect(status().isNoContent());
	}
	
	private ResultMatcher checkIfHasLinksField() {
		return jsonPath("$._links", notNullValue());
	}
}
