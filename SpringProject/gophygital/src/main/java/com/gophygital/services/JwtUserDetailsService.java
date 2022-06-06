package com.gophygital.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gophygital.daos.UserDao;
import com.gophygital.models.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		System.out.println("user--->"+user);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
	
	public User save(User user) {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setCreatedDate(new Date());
		newUser.setActive("Y");
		newUser.setMobile(user.getMobile());
		newUser.setName(user.getName());
		newUser.setLanguage(user.getLanguage());
		newUser.setRole("User");
		userDao.insert(newUser);
		System.out.println("newUser-->"+newUser);
		return userDao.findByUsername(user.getUsername());
	}
	
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public List<User> getAllUser(){
		return userDao.getAllUser();
	}
	
	public void updateUserActiveStatus(User user) {
		userDao.updateUserActiveStatus(user.getUsername(), user.getActive());
	}
}