package com.example.phr.service;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.UserAlreadyExistsException;
import com.example.phr.mobile.models.User;

public interface UserService {

	public void registerUser(User user) throws ServiceException,
			UserAlreadyExistsException;

	public boolean validateUser(String username, String hashedPassword)
			throws ServiceException;

	public User getUserGivenUsername(String username) throws ServiceException,
			OutdatedAccessTokenException;

	public boolean usernameAlreadyExists(String username)
			throws ServiceException;

	public void edit(User user) throws ServiceException,
			OutdatedAccessTokenException;

	public User getUser();
	
	public void loginUser(String username) throws ServiceException, OutdatedAccessTokenException;
	
	public void logoutUser();
}
