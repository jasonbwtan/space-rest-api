package com.jason.space_rest_api.hibernate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class AbstractTimestampEntity {

	@Column(name = "created")
	private Date created = new Date();

	public Date getCreated() {
		return created;
	}

//	@PrePersist
//	protected void onCreate() {
//		created = new Date();
//		System.out.println(created);
//	}
	/*
	 * uncomment this if you want to generate an "updated" timestamp. Must use EntityManager
	 */
	// @Temporal(TemporalType.TIMESTAMP)
	// @Column(name = "updated", nullable = false,updatable=false)
	// private Date updated;

	// @PrePersist
	// protected void onCreate() {
	// updated = created = new Date();
	// }

	// @PreUpdate
	// protected void onUpdate() {
	// updated = new Date();
	// }
}