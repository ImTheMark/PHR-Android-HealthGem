package com.example.phr.web.dao;

import com.example.phr.exceptions.UserAlreadyExistsException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.model.User;

public interface UserDao {

	public boolean validateUser(String username, String password)
			throws WebServerException;

	public User getUserGivenUsername(String username) throws WebServerException;

	public void registerUser(User user) throws WebServerException,
			UserAlreadyExistsException;

}
