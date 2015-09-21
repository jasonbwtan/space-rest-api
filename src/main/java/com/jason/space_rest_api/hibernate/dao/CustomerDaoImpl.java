package com.jason.space_rest_api.hibernate.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.jason.space_rest_api.hibernate.model.Customer;

public class CustomerDaoImpl implements CustomerDao<Customer, Long> {

	private Session currentSession;
	private Transaction currentTransaction;
	static final Logger logger = LogManager.getLogger();

	private static CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl();

	public static CustomerDaoImpl getInstance() {
		return customerDaoImpl;
	}

	// private CustomerDaoImpl() {
	// customerDaoImpl = new CustomerDaoImpl();
	// }
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
		String DATABASE_URL = System.getenv("DATABASE_URL");
		if (DATABASE_URL != null && !DATABASE_URL.equals("")) {
			URI dbUri = null;
			try {
				dbUri = new URI(DATABASE_URL);
			} catch (URISyntaxException e) {
				logger.info("Could not read DATABASE_URL heroku environment variable");
				e.printStackTrace();
			}
			configuration.setProperty(
					"hibernate.connection.url",
					"jdbc:postgresql://" + dbUri.getHost() + ":"
							+ dbUri.getPort() + dbUri.getPath());
			configuration.setProperty("hibernate.connection.username", dbUri
					.getUserInfo().split(":")[0]);
			configuration.setProperty("hibernate.connection.password", dbUri
					.getUserInfo().split(":")[1]);

		} else {
			logger.info("Using default hibernate.cfg.xml properties");
		}
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(builder.build());
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
		openCurrentSessionwithTransaction();
		getCurrentSession().save(entity);
		closeCurrentSessionwithTransaction();
	}

	public void update(Customer entity) {
		openCurrentSessionwithTransaction();
		getCurrentSession().update(entity);
		closeCurrentSessionwithTransaction();
	}

	public void delete(long id) {
		openCurrentSessionwithTransaction();
		Customer customer = (Customer) getCurrentSession().get(Customer.class,
				id);
		getCurrentSession().delete(customer);
		closeCurrentSessionwithTransaction();
	}

	public void delete(Customer entity) {
		openCurrentSessionwithTransaction();
		getCurrentSession().delete(entity);
		closeCurrentSessionwithTransaction();
	}

	public void deleteAll() {
		List<Customer> entityList = findAll();
		for (Customer entity : entityList) {
			delete(entity);
		}
	}

	public Customer findById(Long id) {
		openCurrentSession();
		Customer Customer = (Customer) getCurrentSession().get(Customer.class,
				id);
		closeCurrentSession();
		return Customer;
	}

	@SuppressWarnings("unchecked")
	public List<Customer> findByDate(Date startDate, Date endDate) {
		openCurrentSession();
		Criteria criteria = getCurrentSession().createCriteria(Customer.class)
				.add(Restrictions.ge("startDate", startDate))
				.add(Restrictions.le("endDate", endDate));
		List<Customer> list = (List<Customer>) criteria.list();
		closeCurrentSession();

		return list;

	}

	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
		openCurrentSession();
		List<Customer> books = (List<Customer>) getCurrentSession()
				.createQuery("from Customer").list();
		closeCurrentSession();
		return books;
	}

}
