package com.example.phr.serviceimpl;

import java.text.ParseException;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.UserAlreadyExistsException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.models.User;
import com.example.phr.service.UserService;
import com.example.phr.tools.DateTimeParser;
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
	public boolean usernameAlreadyExists(String username)
			throws ServiceException {
		try {
			return userDao.usernameAlreadyExists(username);
		} catch (WebServerException e) {
			throw new ServiceException("An error occured in the user service",
					e);
		}
	}

	@Override
	public void edit(User user) throws ServiceException,
			OutdatedAccessTokenException {
		try {
			userDao.edit(user);
		} catch (WebServerException e) {
			throw new ServiceException("An error occured in the user service",
					e);
		}
	}

	@Override
	public User getUser() {
		User user = new User();
		user.setAllergies(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.ALLERGIES));
		user.setContactNumber(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.NUMBER));
		try {
			user.setDateOfBirth(DateTimeParser.getTimestamp(HealthGem
					.getSharedPreferences().loadPreferences(
							SPreference.BIRTHDATE)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setEmail(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.EMAIL));
		user.setEmergencyContactNumber(HealthGem.getSharedPreferences()
				.loadPreferences(SPreference.CONTACTPERSONNUMBER));
		user.setEmergencyPerson(HealthGem.getSharedPreferences()
				.loadPreferences(SPreference.CONTACTPERSON));
		user.setGender(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.GENDER));

		double height = getDouble(
				PreferenceManager.getDefaultSharedPreferences(HealthGem
						.getContext()), SPreference.HEIGHT, 100.0);
		// user.setHeight(Double.parseDouble(HealthGem.getSharedPreferences()
		// .loadPreferences(SPreference.HEIGHT)));
		user.setHeight(height);
		user.setKnownHealthProblems(HealthGem.getSharedPreferences()
				.loadPreferences(SPreference.KNOWNHEALTHPROBLEMS));
		user.setName(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.NAME));
		user.setUsername(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.USERNAME));
		double weight = getDouble(
				PreferenceManager.getDefaultSharedPreferences(HealthGem
						.getContext()), SPreference.WEIGHT, 100.0);
		// user.setWeight(Double.parseDouble(HealthGem.getSharedPreferences()
		// .loadPreferences(SPreference.WEIGHT)));
		user.setWeight(weight);
		return user;
	}

	double getDouble(final SharedPreferences prefs, final String key,
			final double defaultValue) {
		if (!prefs.contains(key))
			return defaultValue;

		return Double.longBitsToDouble(prefs.getLong(key, (long) 0d));
	}
}
