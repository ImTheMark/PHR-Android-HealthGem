package com.example.phr.serviceimpl;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.UserAlreadyExistsException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.models.User;
import com.example.phr.service.UserService;
import com.example.phr.web.dao.UserDao;
import com.example.phr.web.daoimpl.UserDaoImpl;

public class UserServiceImpl implements UserService {

	UserDao userDao;

	public UserServiceImpl() {
		userDao = new UserDaoImpl();
	}

	@Override
	public void registerUser(User user) throws ServiceException,
			UserAlreadyExistsException {
		try {
			userDao.registerUser(user);
		} catch (WebServerException e) {
			throw new ServiceException("Error in registration", e);
		}
	}

	@Override
	public boolean validateUser(String username, String password)
			throws ServiceException {
		try {
			return userDao.validateUser(username, password);
		} catch (WebServerException e) {
			throw new ServiceException("An error occured in the user service",
					e);
		}
	}

	@Override
	public User getUserGivenUsername(String username) throws ServiceException,
			OutdatedAccessTokenException {
		try {
			return userDao.getUserGivenUsername(username);
		} catch (WebServerException e) {
			throw new ServiceException("An error occured in the user service",
					e);
		}
	}

	@Override
	public boolean usernameAlreadyExists(String username) {
		return usernameAlreadyExists(username);
	}

	@Override
	public User getUser() {
		User user = new User();
		user.setAllergies(HealthGem.getSharedPreferences().loadPreferences(SPreference.ALLERGIES));
		user.setContactNumber(HealthGem.getSharedPreferences().loadPreferences(SPreference.NUMBER));
		user.setDateOfBirth(HealthGem.getSharedPreferences().loadPreferences(SPreference.BIRTHDATE));
		user.setEmail(HealthGem.getSharedPreferences().loadPreferences(SPreference.EMAIL));
		user.setEmergencyContactNumber(HealthGem.getSharedPreferences().loadPreferences(SPreference.CONTACTPERSONNUMBER));
		user.setEmergencyPerson(HealthGem.getSharedPreferences().loadPreferences(SPreference.CONTACTPERSON));
		user.setGender(HealthGem.getSharedPreferences().loadPreferences(SPreference.GENDER));
		user.setHeight(Double.parseDouble(HealthGem.getSharedPreferences().loadPreferences(SPreference.HEIGHT)));
		user.setKnownHealthProblems(HealthGem.getSharedPreferences().loadPreferences(SPreference.KNOWNHEALTHPROBLEMS));
		user.setName(HealthGem.getSharedPreferences().loadPreferences(SPreference.NAME));
		user.setUsername(HealthGem.getSharedPreferences().loadPreferences(SPreference.USERNAME));
		user.setWeight(Double.parseDouble(HealthGem.getSharedPreferences().loadPreferences(SPreference.WEIGHT)));
		return user;
	}
}
