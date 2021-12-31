package com.Niche.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;

import java.io.Serializable;


@Entity
@Table(name = "request_tbl")
public class Request implements Serializable {

	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String request;
	private String subject;
	private int statusCode;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	
	public Request(){}
	
	public Request(String subject, String request) {
		this.subject = subject;
		this.request = request;
	}
	
}
