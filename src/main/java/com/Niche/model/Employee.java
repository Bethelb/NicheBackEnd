package com.Niche.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "requests")
public class Employee {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String emailId;
	private String request;
	private String subject;
	private boolean status;

	public Employee() {}
	
	public Employee(String firstName, String lastName, String emailId, String request) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.request = request;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "First Name", nullable = false)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "Last Name", nullable = false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "Email", nullable = false)
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name = "Subject", nullable = false)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name = "Request", nullable = false)
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	
	@Override
	public String toString() {
		return "User ID: " + id + "\n First Name: " + firstName + "\n Last Name: " + lastName + "\n Email: " + emailId;
	}
	
}
