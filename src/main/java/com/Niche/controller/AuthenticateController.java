 package com.Niche.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Niche.exception.UserNotFoundException;
//import com.Niche.model.Employee;
import com.Niche.model.JwtRequest;
import com.Niche.model.JwtResponse;
import com.Niche.model.User;
//import com.Niche.repository.EmployeeRepository;
import com.Niche.security.JwtUtils;
import com.Niche.service.UserDetailsServiceImpl;
import com.Niche.service.UserServiceImpl;

import io.jsonwebtoken.lang.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserServiceImpl userService;
	
//	@Autowired
//	private EmployeeRepository employeeRepository;
//	

	//generate token
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest
			jwtRequest) throws Exception{
		
		try {
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			
		} catch (UserNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("User not found");
		}
		
		UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
		String token= this.jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
		User userDb = userService.getUser(user.getUsername());
		if (userDb == null) {
			return ResponseEntity.ok(userService.saveUser(user));
		} else {
			return ResponseEntity.ok("User already exists!");
		}
	}
//
//	@PostMapping("/userrequests/add")
//	public Employee createEmployee(@Valid @RequestBody Employee employee) {
//		return employeeRepository.save(employee);
//	}
//
//	@GetMapping("/userrequests")
//	public List<Employee> getAllEmployees() {
//		return employeeRepository.findAll();
//	}
//


	private void authenticate(String username, String password) throws Exception {
//		Objects.requireNonNull(username);
//		Objects.requireNonNull(password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException e) {
			// TODO: handle exception
			throw new Exception("USER DISABLED"+e.getMessage());
		}catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new Exception("Invalid Credentials"+e.getMessage());
		}
		
	}
	
	//return the details of current user
	@GetMapping(value = "/{username}")
	public User getCurrentUser(Principal principal) {
		return ((User)this.userDetailsServiceImpl.loadUserByUsername(principal.getName()));
	}
}
