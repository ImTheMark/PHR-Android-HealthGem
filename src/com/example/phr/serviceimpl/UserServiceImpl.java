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
import com.example.phr.mobile.dao.MobileUserDao;
import com.example.phr.mobile.daoimpl.MobileUserDaoImpl;
import com.example.phr.mobile.models.User;
import com.example.phr.service.UserService;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.web.dao.UserDao;
import com.example.phr.web.daoimpl.UserDaoImpl;

public class UserServiceImpl implements UserService {

	UserDao userDao;
	MobileUserDao mobileUserDao;

	public UserServiceImpl() {
		userDao = new UserDaoImpl();
		mobileUserDao = new MobileUserDaoImpl();
	}

	@Override
	public void registerUser(User user) throws ServiceException,
			UserAlreadyExistsException {
		try {
			mobileUserDao.registerUser();
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
			mobileUserDao.editUser(user);
		} catch (WebServerException e) {
			throw new ServiceException("An error occured in the user service",
					e);
		}
	}

	@Override
	public User getUser() {
		return mobileUserDao.getUser();
	}

	double getDouble(final SharedPreferences prefs, final String key,
			final double defaultValue) {
		if (!prefs.contains(key))
			return defaultValue;

		return Double.longBitsToDouble(prefs.getLong(key, (long) 0d));
	}

	@Override
	public void logoutUser() {
		mobileUserDao.logoutUser();
	}

	@Override
	public void loginUser(String username) throws ServiceException, OutdatedAccessTokenException {
		mobileUserDao.loginUser(getUserGivenUsername(username));
	}
}
