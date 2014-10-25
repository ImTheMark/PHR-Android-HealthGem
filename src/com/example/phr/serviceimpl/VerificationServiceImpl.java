package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.service.VerificationService;
import com.example.phr.web.dao.WebVerificationDao;
import com.example.phr.web.daoimpl.WebVerificationDaoImpl;

public class VerificationServiceImpl implements VerificationService {

	WebVerificationDao webVerificationDao = new WebVerificationDaoImpl();

	@Override
	public void updateListOfUnverifiedPosts() throws ServiceException,
			OutdatedAccessTokenException {
		try {
			webVerificationDao.updateListOfUnverifiedPosts();
		} catch (WebServerException e) {
			throw new ServiceException("Error", e);
		}
	}

	@Override
	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts()
			throws ServiceException, OutdatedAccessTokenException {
		try {
			return webVerificationDao.getAllUnverifiedFoodPosts();
		} catch (WebServerException e) {
			throw new ServiceException("Error", e);
		}
	}

	@Override
	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts()
			throws ServiceException, OutdatedAccessTokenException {
		try {
			return webVerificationDao.getAllUnverifiedActivityPosts();
		} catch (WebServerException e) {
			throw new ServiceException("Error", e);
		}
	}

	@Override
	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts()
			throws ServiceException, OutdatedAccessTokenException {
		try {
			return webVerificationDao.getAllUnverifiedRestaurantPosts();
		} catch (WebServerException e) {
			throw new ServiceException("Error", e);
		}
	}

	@Override
	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts()
			throws ServiceException, OutdatedAccessTokenException {
		try {
			return webVerificationDao
					.getAllUnverifiedSportsEstablishmentPosts();
		} catch (WebServerException e) {
			throw new ServiceException("Error", e);
		}
	}

	@Override
	public void delete(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException {
		try {
			webVerificationDao.delete(entry);
		} catch (WebServerException e) {
			throw new ServiceException("error", e);
		}
	}

	@Override
	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException {
		try {
			webVerificationDao.delete(entry);
		} catch (WebServerException e) {
			throw new ServiceException("error", e);
		}
	}

	@Override
	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException {
		try {
			webVerificationDao.delete(entry);
		} catch (WebServerException e) {
			throw new ServiceException("error", e);
		}
	}

	@Override
	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException {
		try {
			webVerificationDao.delete(entry);
		} catch (WebServerException e) {
			throw new ServiceException("error", e);
		}
	}

}
