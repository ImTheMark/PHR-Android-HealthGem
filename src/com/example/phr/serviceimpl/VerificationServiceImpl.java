package com.example.phr.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileVerificationDao;
import com.example.phr.mobile.daoimpl.MobileVerificationDaoImpl;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.service.VerificationService;
import com.example.phr.web.dao.WebVerificationDao;
import com.example.phr.web.daoimpl.WebVerificationDaoImpl;

public class VerificationServiceImpl implements VerificationService {

	WebVerificationDao webVerificationDao = new WebVerificationDaoImpl();
	MobileVerificationDao mobileVerificationDao = new MobileVerificationDaoImpl();

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

	@Override
	public List<TrackerEntry> getAll() throws ServiceException,
			OutdatedAccessTokenException {
		List<TrackerEntry> list = new ArrayList<TrackerEntry>();
		
		list.addAll(getAllUnverifiedActivityPosts());
		list.addAll(getAllUnverifiedFoodPosts());
		list.addAll(getAllUnverifiedRestaurantPosts());
		list.addAll(getAllUnverifiedSportsEstablishmentPosts());
		
		Collections.sort(list, new Comparator<TrackerEntry>() {
			@Override
			public int compare(TrackerEntry e1, TrackerEntry e2) {
				return e1.getTimestamp().compareTo(e2.getTimestamp());
			}
		});

		Collections.reverse(list);
		
		return list;
	}

	@Override
	public void addFoodListToTemporaryDatabase(List<Food> foodList) {
		mobileVerificationDao.addFoodList(foodList);
	}

	@Override
	public void addActivityListToTemporaryDatabase(List<Activity> activityList) {
		mobileVerificationDao.addActivityList(activityList);
	}

	@Override
	public List<Food> getFoodList() {
		return mobileVerificationDao.getFoodList();
	}

	@Override
	public List<Activity> getActivityList() {
		return mobileVerificationDao.getActivityList();
	}

}
