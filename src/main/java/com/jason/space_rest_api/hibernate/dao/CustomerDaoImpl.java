package com.jason.space_rest_api.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.jason.space_rest_api.hibernate.model.Customer;

public class CustomerDaoImpl implements CustomerDao<Customer, Long> {

	private Session currentSession;
	private Transaction currentTransaction;

	public CustomerDaoImpl() {
	}

	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	public void persist(Customer entity) {
		getCurrentSession().save(entity);
	}

	public void update(Customer entity) {
		getCurrentSession().update(entity);
	}

	public Customer findById(Long id) {
		Customer Customer = (Customer) getCurrentSession().get(Customer.class, id);
		return Customer; 
	}

	public void delete(Customer entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
		List<Customer> books = (List<Customer>) getCurrentSession().createQuery("from Customer").list();
		return books;
	}

	public void deleteAll() {
		List<Customer> entityList = findAll();
		for (Customer entity : entityList) {
			delete(entity);
		}
	}

}
