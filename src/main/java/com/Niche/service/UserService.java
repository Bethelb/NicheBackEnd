package com.Niche.service;

import java.util.Set;

import com.Niche.model.User;
import com.Niche.model.UserRole;

public interface UserService {

	
	//create user
		public User createUser(User user, Set<UserRole> userRoles) throws Exception;

		//get user by username
		public User getUser(String username);
		
		//delete user
		public void deleteUser(Long userId);
}
