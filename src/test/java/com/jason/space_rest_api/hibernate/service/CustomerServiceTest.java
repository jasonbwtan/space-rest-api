package com.jason.space_rest_api.hibernate.service;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jason.space_rest_api.hibernate.dao.CustomerDaoImpl;
import com.jason.space_rest_api.hibernate.model.Customer;

public class CustomerServiceTest {
	/*
	 * Tests integration of data access layer
	 */
	CustomerDaoImpl dao = CustomerDaoImpl.getInstance();

	@Before
	public void beforeTest() {
		dao.deleteAll();

	}

	@After
	public void afterTest() {
	}

	public static void main(String[] args) {
		Customer c1 = new Customer("JT1", "jason@testmail.com", "07123456789",
				"com.JT", new Date(1l), new Date(
						System.currentTimeMillis()), 5, 1, "it was great");
		CustomerDaoImpl dao = new CustomerDaoImpl();
		dao.persist(c1);
		System.out.println(dao.findByDate(new Date(System.currentTimeMillis()-40000000000l), new Date(System.currentTimeMillis())).size());
		//Customer cc = dao.findById(1l);
		//System.out.println(cc.getEmail());
		System.out.println(dao.findAll().size());
		//dao.deleteAll();
	}

	@Test
	public void testPersist() {
		Customer c1 = new Customer("JT1", "jason@testmail.com", "07123456789",
				"com.JT", new Date(System.currentTimeMillis()), new Date(
						System.currentTimeMillis()), 5, 1, "it was great");
		dao.persist(c1);
		Customer cResult = dao.findAll().get(0);
		assertTrue("persist should be the same",c1.equals(cResult));
		
	}

	@Test
	public void testFindById() {
		Customer c1 = new Customer("JT1", "jason@testmail.com", "07123456789",
				"com.JT", new Date(System.currentTimeMillis()), new Date(
						System.currentTimeMillis()), 5, 1, "it was great");
		dao.persist(c1);
		Customer cResult = dao.findById(c1.getId());
		System.out.println(cResult.getEmail());
		assertTrue(c1.equals(cResult));
	}

	@Test
	public void testDelete() {
		Customer c1 = new Customer("JT1", "jason@testmail.com", "07123456789",
				"com.JT", new Date(System.currentTimeMillis()), new Date(
						System.currentTimeMillis()), 5, 1, "it was great");
		dao.persist(c1);
		dao.delete(c1.getId());
		assertTrue(dao.findAll().isEmpty());
	}

	@Test
	public void testDeleteAll() {

	}
}
