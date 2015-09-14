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

	public Customer(String name, String organisation, Date date,
			int numberOfPeople, int catering, int overtime) {
		super();
		this.name = name;
		this.organisation = organisation;
		this.date = date;
		this.numberOfPeople = numberOfPeople;
		this.catering = catering;
		this.overtime = overtime;
	}

	public Customer(long id, String name, String organisation, Date date,
			int numberOfPeople, int catering, int overtime) {
		super();
		this.id = id;
		this.name = name;
		this.organisation = organisation;
		this.date = date;
		this.numberOfPeople = numberOfPeople;
		this.catering = catering;
		this.overtime = overtime;
	}

	public Customer() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "organisation")
	private String organisation;

	@Column(name = "date")
	@Type(type = "timestamp")
	private Date date;

	@Column(name = "numberOfPeople")
	private int numberOfPeople;

	@Column(name = "catering")
	private int catering;

	@Column(name = "overtime")
	private int overtime;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOrganisation() {
		return organisation;
	}

	public Date getDate() {
		return date;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public int isCatering() {
		return catering;
	}

	public int isOvertime() {
		return overtime;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public void setCatering(int catering) {
		this.catering = catering;
	}

	public void setOvertime(int overtime) {
		this.overtime = overtime;
	}

	@Override
	public String toString() {
		return String
				.format("Customer [id:%s, name:%s,organisation:%s,date:%s,numOfPeople:%s,catering:%b,overtime:%b]",
						id, name, organisation, date, numberOfPeople, catering,
						overtime);
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Customer) {
			Customer customerArg = (Customer) o;
			// this.id == customerArg.id &&
			if (this.name.equals(customerArg.name)
					&& this.organisation.equals(customerArg.organisation)
					&& this.date.equals(customerArg.date)
					&& this.numberOfPeople == customerArg.numberOfPeople
					&& this.catering == customerArg.catering
					&& this.overtime == customerArg.overtime) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Customer c = new Customer("JT", "com.JT", new Date(
				System.currentTimeMillis()), 5, TRUE, TRUE);
		System.out.println(c);
		System.out.println(c.equals(c));
	}
}
