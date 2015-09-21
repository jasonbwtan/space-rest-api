package com.jason.space_rest_api.hibernate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "REQUESTS")
public class Customer extends AbstractTimestampEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int TRUE = 1;
	public static final int FALSE = 0;

	public Customer(String name, String email, String phone,
			String organisation, Date startDate, Date endDate,
			int numberOfPeople, String catering,
			String additionalComments) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.organisation = organisation;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numberOfPeople = numberOfPeople;
		this.catering = catering;
		this.additionalComments = additionalComments;
	}

	public Customer(long id, String name, String email, String phone,
			String organisation, Date startDate, Date endDate,
			int numberOfPeople, String catering, int overtime,
			String additionalComments) {
		this(name, email, phone, organisation, startDate, endDate,
				numberOfPeople, catering, additionalComments);
		this.id = id;

	}

	public Customer() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "organisation")
	private String organisation;

	@Column(name = "startDate")
	@Type(type = "timestamp")
	private Date startDate;

	@Column(name = "endDate")
	@Type(type = "timestamp")
	private Date endDate;

	@Column(name = "numberOfPeople")
	private int numberOfPeople;

	@Column(name = "catering")
	private String catering;

	@Column(name = "additionalComments")
	private String additionalComments;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getOrganisation() {
		return organisation;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}	
	
	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public String isCatering() {
		return catering;
	}

	public String getAdditionalComments() {
		return additionalComments;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public void setCatering(String catering) {
		this.catering = catering;
	}

	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}

	@Override
	public String toString() {
		return String
				.format("Customer [id:%s, name:%s,organisation:%s,startDate:%s,endDate:%s,numOfPeople:%s,catering:%b]",
						id, name, organisation, startDate,endDate, numberOfPeople, catering);
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Customer) {
			Customer customerArg = (Customer) o;
			// this.id == customerArg.id &&
			if (this.name.equals(customerArg.name)
					&& this.email.equals(customerArg.email)
					&& this.phone.equals(customerArg.phone)
					&& this.organisation.equals(customerArg.organisation)
					&& this.startDate.equals(customerArg.startDate)
					&& this.endDate.equals(customerArg.startDate)
					&& this.numberOfPeople == customerArg.numberOfPeople
					&& this.catering == customerArg.catering) {
				return true;
			}
		}
		return false;
	}

	// public static void main(String[] args) {
	// Customer c = new Customer("JT", "com.JT", new Date(
	// System.currentTimeMillis()), 5, TRUE, TRUE);
	// System.out.println(c);
	// System.out.println(c.equals(c));
	// }
}
