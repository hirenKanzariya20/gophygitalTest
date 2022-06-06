package com.gophygital.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(name = "UNQ_Username", columnNames = {"username"}))
public class User {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String name;
	
	@Column
	private String mobile;
	
	@Column
	private String language;
	
	
	@Column
	private String role;
	
	@Column
	private Date createdDate;
	
	@Column
	private String active;
	
	
	@JsonInclude
	@Transient
	private String token;
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", mobile="
				+ mobile + ", language=" + language + ", role=" + role + ", createdDate=" + createdDate + ", active="
				+ active + ", token=" + token + "]";
	}
	
	

}