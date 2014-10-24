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
	public void updateListOfUnverifiedPosts() throws ServiceException {
		webVerificationDao.updateListOfUnverifiedPosts();
	}

	@Override
	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts()
			throws ServiceException {
		return webVerificationDao.getAllUnverifiedFoodPosts();
	}

	@Override
	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts()
			throws ServiceException {
		return webVerificationDao.getAllUnverifiedActivityPosts();
	}

	@Override
	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts()
			throws ServiceException {
		return webVerificationDao.getAllUnverifiedRestaurantPosts();
	}

	@Override
	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts()
			throws ServiceException {
		return webVerificationDao.getAllUnverifiedSportsEstablishmentPosts();
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
