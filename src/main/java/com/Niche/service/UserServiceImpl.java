package com.Niche.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Niche.exception.ResourceNotFoundException;
import com.Niche.model.User;
import com.Niche.model.UserRole;
import com.Niche.repository.RoleRepository;
import com.Niche.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	//creating user
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		User local = this.userRepository.findByUsername(user.getUsername());
		
		if(local != null) {
			System.out.println("Error: Try Again, User already taken...");
			throw new ResourceNotFoundException("User is already there");
		}else {
			//user create
			for(UserRole ur:userRoles) {
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			local = this.userRepository.save(user);
		}
		
		return local;
	}

	
	public User saveUser(User user) {
		User local = this.userRepository.save(user);
		return local;
	}
	
	
	
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return this.userRepository.findByUsername(username);
	}

	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		this.userRepository.deleteById(userId);
	}

}
