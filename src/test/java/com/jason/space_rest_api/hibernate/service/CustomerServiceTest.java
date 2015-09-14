package com.jason.space_rest_api.hibernate.service;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jason.space_rest_api.hibernate.model.Customer;
import com.javacodegeeks.snippets.enterprise.hibernate.service.CustomerService;

public class CustomerServiceTest {
	/*
	 * Tests integration of data access layer
	 */
	CustomerService customerService = CustomerService.getInstance();

	@Before
	public void beforeTest() {
		customerService.deleteAll();

	}

	@After
	public void afterTest() {
	}

	public static void main(String[] args) {

	}

	@Test
	public void testPersist() {
		Customer c1 = new Customer("JT1", "com.JT", new Date(
				System.currentTimeMillis()), 5, 1, 1);
		customerService.persist(c1);
		Customer cResult = customerService.findAll().get(0);
		assertTrue(c1.equals(cResult));
	}

	@Test
	public void testFindById() {
		Customer c1 = new Customer("JT1", "com.JT", new Date(
				System.currentTimeMillis()), 5, 1, 1);
		customerService.persist(c1);
		Customer cResult = customerService.findById(c1.getId());
		assertTrue(c1.equals(cResult));
	}

	@Test
	public void testDelete() {
		Customer c1 = new Customer("JT1", "com.JT", new Date(
				System.currentTimeMillis()), 5, 1, 1);
		customerService.persist(c1);
		customerService.delete(c1.getId());
		assertTrue(customerService.findAll().isEmpty());
	}

	@Test
	public void testDeleteAll() {

	}
}
