package com.example.phr.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.phr.exceptions.DataAccessException;
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
			throw new ServiceException(
					"An error occured while trying update list ofunverify", e);

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
			mobileVerificationDao.deleteUnverifiedFoodPost(entry);
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
			mobileVerificationDao.deleteUnverifiedActivityPost(entry);
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
			mobileVerificationDao.deleteUnverifiedRestaurantPost(entry);
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
			mobileVerificationDao.deleteUnverifiedSportEstablishmentPost(entry);
		} catch (WebServerException e) {
			throw new ServiceException("error", e);
		}
	}

	@Override
	public void getAllFromWebDB() throws ServiceException,
			OutdatedAccessTokenException {
		List<TrackerEntry> list = new ArrayList<TrackerEntry>();

		list.addAll(getAllUnverifiedActivityPosts());
		list.addAll(getAllUnverifiedFoodPosts());
		list.addAll(getAllUnverifiedRestaurantPosts());
		list.addAll(getAllUnverifiedSportsEstablishmentPosts());

		Log.e("LIST SIZE ", list.size() + "");

		try {
			mobileVerificationDao.emptyVerificationDatabase();
			for (TrackerEntry entry : list) {
				if (entry.getClass().equals(UnverifiedFoodEntry.class))
					mobileVerificationDao
							.addUnverifiedFoodPost((UnverifiedFoodEntry) entry);
				else if (entry.getClass().equals(UnverifiedActivityEntry.class))
					mobileVerificationDao
							.addUnverifiedActivityPost((UnverifiedActivityEntry) entry);
				else if (entry.getClass().equals(
						UnverifiedRestaurantEntry.class))
					mobileVerificationDao
							.addUnverifiedRestaurantPost((UnverifiedRestaurantEntry) entry);
				else if (entry.getClass().equals(
						UnverifiedSportsEstablishmentEntry.class))
					mobileVerificationDao
							.addUnverifiedSportEstablishmentPost((UnverifiedSportsEstablishmentEntry) entry);
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void storeEncodedImage(String encodedImage) {
		mobileVerificationDao.storeEncodedImage(encodedImage);
	}

	@Override
	public String getImageFileName() {
		return mobileVerificationDao.getImageFileName();
	}

	@Override
	public int getUnverifiedPostsCount() {
		return mobileVerificationDao.getUnverifiedPostsCount();
	}

	@Override
	public List<TrackerEntry> getAllFromMobileDB() {
		try {
			return mobileVerificationDao.getAllUnverifiedPosts();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<TrackerEntry>();
	}

	@Override
	public UnverifiedFoodEntry getUnverifiedFoodPostFromWebDB(
			UnverifiedFoodEntry entry) throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnverifiedActivityEntry getUnverifiedActivityPostFromWebDB(
			UnverifiedActivityEntry entry) throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnverifiedRestaurantEntry getUnverifiedRestaurantPostFromWebDB(
			UnverifiedRestaurantEntry entry) throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnverifiedSportsEstablishmentEntry getUnverifiedSportsEstablishmentPostFromWebDB(
			UnverifiedSportsEstablishmentEntry entry) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			return webVerificationDao.get(entry);
		} catch (WebServerException e) {
			throw new ServiceException("An error has occurred", e);
		}
	}

	@Override
	public List<Food> getFoodListGivenRestaurantID(int ID) {
		return mobileVerificationDao.getFoodListGivenRestaurantID(ID);
	}

	@Override
	public List<Activity> getActivityListGivenEstablishmentID(int ID) {
		return mobileVerificationDao.getActivityListGivenEstablishmentID(ID);
	}

}
