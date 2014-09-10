package com.example.phr.service;

import com.example.phr.exceptions.IPBlockedException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.UserAlreadyExistsException;
import com.example.phr.model.User;

public interface UserService {

	public void registerUser(User user) throws ServiceException,
			UserAlreadyExistsException;

	public boolean validateUser(String username, String hashedPassword)
			throws ServiceException, IPBlockedException;

	public User getUserGivenUsername(String username) throws ServiceException;
}
