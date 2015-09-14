package com.javacodegeeks.snippets.enterprise.hibernate.service;

import java.util.List;

import com.jason.space_rest_api.hibernate.dao.CustomerDao;
import com.jason.space_rest_api.hibernate.dao.CustomerDaoImpl;
import com.jason.space_rest_api.hibernate.model.Customer;


public class CustomerService {
	/*
	 * This service class is needed to retain the result object from the session query after the session closes
	 * If this logic was moved to the DaoImpl you'll receive a session is not open exception
	 */
	private static CustomerDaoImpl customerDaoImpl;
	private static final CustomerService customerService = new CustomerService();
	
	public static CustomerService getInstance(){
		return customerService;
	}
	private CustomerService() {
		customerDaoImpl = new CustomerDaoImpl();
	}

	public void persist(Customer entity) {
		customerDaoImpl.openCurrentSessionwithTransaction();
		customerDaoImpl.persist(entity);
		customerDaoImpl.closeCurrentSessionwithTransaction();
	}

	public void update(Customer entity) {
		customerDaoImpl.openCurrentSessionwithTransaction();
		customerDaoImpl.update(entity);
		customerDaoImpl.closeCurrentSessionwithTransaction();
	}

	public Customer findById(long id) {
		customerDaoImpl.openCurrentSession();
		Customer Customer = customerDaoImpl.findById(id);
		customerDaoImpl.closeCurrentSession();
		return Customer;
	}

	public void delete(long id) {
		customerDaoImpl.openCurrentSessionwithTransaction();
		Customer Customer = customerDaoImpl.findById(id);
		customerDaoImpl.delete(Customer);
		customerDaoImpl.closeCurrentSessionwithTransaction();
	}

	public List<Customer> findAll() {
		customerDaoImpl.openCurrentSession();
		List<Customer> books = customerDaoImpl.findAll();
		customerDaoImpl.closeCurrentSession();
		return books;
	}

	public void deleteAll() {
		customerDaoImpl.openCurrentSessionwithTransaction();
		customerDaoImpl.deleteAll();
		customerDaoImpl.closeCurrentSessionwithTransaction();
	}

	public CustomerDaoImpl customerDaoImpl() {
		return customerDaoImpl;
	}
}
