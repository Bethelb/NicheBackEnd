package com.Niche.exception;

public class UserNotFoundException extends Exception{
	public UserNotFoundException() {
		// TODO Auto-generated constructor stub
		super("User with this Username not found in Database !!");
	}
	
	public UserNotFoundException(String msg){
		super(msg);
	}
}
