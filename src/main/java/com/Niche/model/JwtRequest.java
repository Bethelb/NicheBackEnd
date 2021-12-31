package com.Niche.model;

public class JwtRequest {
	private String username;
	private String password;
	private static final long serialVersionUID = 5926468583005150707L;
	
	public JwtRequest() {
		// TODO Auto-generated constructor stub
	}
	public JwtRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
