
package com.javacodegeeks.snippets.enterprise.hibernate.service;
import java.util.Date;

import com.jason.space_rest_api.deprecated.CustomerService;

//import java.util.Date;
//import java.util.List;
//
//import com.jason.space_rest_api.hibernate.model.Customer;
//
public class App {
	public static void main(String[] args) {
		CustomerService customerService = CustomerService.getInstance();
//		customerService.deleteAll();
//		Customer c1 = new Customer("JT1","com.JT",new Date(),5,Customer.TRUE,Customer.TRUE);
//		Customer c2 = new Customer("JT2","com.JT",new Date(System.currentTimeMillis()),5,Customer.TRUE,Customer.TRUE);
//		Customer c3 = new Customer("JT3","com.JT",new Date(System.currentTimeMillis()),5,Customer.TRUE,Customer.TRUE);
//
//		//persist
//		customerService.persist(c1);
//		List<Customer> result = customerService.findAll();
//		Customer cResult = result.get(0);
//		
//		//update
//		customerService.persist(c2);
//		c2.setName("JT2Updated");
//		customerService.update(c2);
//		
//		//delete
//		customerService.delete(c2.getId());
//		
//
//		
//		
//		System.exit(0);
//1442750674147
		System.out.println(System.currentTimeMillis());
		Date startDate = new Date(442750674147l);
		System.out.println(startDate);
		Date endDate = new Date(System.currentTimeMillis()+40000l);
		System.out.println(customerService.findByDate(startDate, endDate));
		System.exit(0);
		
		
	}
}
