package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;

public interface VerificationService {

	public List<Food> getFoodListGivenRestaurantID(int id);

	public List<Activity> getActivityListGivenEstablishmentID(int ID);

	public void storeEncodedImage(String encodedImage);

	public String getImageFileName();

	public void updateListOfUnverifiedPosts() throws ServiceException,
			OutdatedAccessTokenException;

	public int getUnverifiedPostsCount();

	public void getAllFromWebDB() throws ServiceException,
			OutdatedAccessTokenException;

	public List<TrackerEntry> getAllFromMobileDB();

	public UnverifiedFoodEntry getUnverifiedFoodPostFromWebDB(
			UnverifiedFoodEntry entry) throws ServiceException,
			OutdatedAccessTokenException;

	public UnverifiedActivityEntry getUnverifiedActivityPostFromWebDB(
			UnverifiedActivityEntry entry) throws ServiceException,
			OutdatedAccessTokenException;

	public UnverifiedRestaurantEntry getUnverifiedRestaurantPostFromWebDB(
			UnverifiedRestaurantEntry entry) throws ServiceException,
			OutdatedAccessTokenException;

	public UnverifiedSportsEstablishmentEntry getUnverifiedSportsEstablishmentPostFromWebDB(
			UnverifiedSportsEstablishmentEntry entry) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException;

	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts()
			throws ServiceException, OutdatedAccessTokenException;

	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts()
			throws ServiceException, OutdatedAccessTokenException;

	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts()
			throws ServiceException, OutdatedAccessTokenException;

	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts()
			throws ServiceException, OutdatedAccessTokenException;

	public void delete(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException;

	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException;

	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException;

	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException;
}
