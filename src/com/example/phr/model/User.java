package com.example.phr.model;

import java.util.Date;

public class User {

	private long id;
	private String username;
	private String password;
	private String name;
	private Date dateOfBirth;

	public User() {
		super();
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String name, Date dateOfBirth) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public User(long id, String username, String password, String name,
			Date dateOfBirth) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
