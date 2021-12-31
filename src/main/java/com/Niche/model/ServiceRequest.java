package com.Niche.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "requests")
public class ServiceRequest {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String emailId;
	private String subject;
	private String request;


	public ServiceRequest() {}
	
	public ServiceRequest(String emailId, String subject, String request) {
		this.emailId = emailId;
		this.subject = subject;
		this.request = request;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
		return "User ID: " + id + "\n Email: " + emailId;
	}
	
}
