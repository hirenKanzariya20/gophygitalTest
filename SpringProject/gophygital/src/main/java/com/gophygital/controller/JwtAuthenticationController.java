package com.gophygital.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gophygital.config.JwtTokenUtil;
import com.gophygital.models.User;
import com.gophygital.services.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User authenticationRequest) throws Exception {
		System.out.println("authenticationRequest--->"+authenticationRequest);
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		System.out.println("authenticationRequest1--->"+authenticationRequest);
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		System.out.println("token "+ token);
		User user = userDetailsService.findByUsername(authenticationRequest.getUsername());
		user.setToken(token);
		System.out.println("userDetails "+ userDetails);
		return ResponseEntity.ok(user);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
		System.out.println("User--->"+user);
		return ResponseEntity.ok(userDetailsService.save(user));
	}
	
	@RequestMapping(value = "/findByUsername", method = RequestMethod.POST)
	public ResponseEntity<?> findByUsername(@RequestBody User user) throws Exception {
		System.out.println("User--->"+user);
		return ResponseEntity.ok(userDetailsService.findByUsername(user.getUsername()));
	}
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers() throws Exception {
		
		return ResponseEntity.ok(userDetailsService.getAllUser());
	}
	
	@RequestMapping(value = "/updateUserActiveStatus", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserActiveStatus(@RequestBody User user) throws Exception {
		userDetailsService.updateUserActiveStatus(user);
		return ResponseEntity.ok("\"{ Success: success }\"");
	}
}