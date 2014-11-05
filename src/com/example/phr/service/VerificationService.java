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
	
	public void addFoodListToTemporaryDatabase(List<Food> foodList);
	
	public void addActivityListToTemporaryDatabase(List<Activity> activityList);
	
	public List<Food> getFoodList();
	
	public List<Activity> getActivityList();
	
	public void storeEncodedImage(String encodedImage);
	
	public String getImageFileName();
	
	public void setUnverifiedPostsCount(int count);
	
	public void decreaseUnverifiedPostsCount();
	
	public int getUnverifiedPostsCount();
	
	public void updateListOfUnverifiedPosts() throws ServiceException,
			OutdatedAccessTokenException;

	public List<TrackerEntry> getAll() throws ServiceException,
			OutdatedAccessTokenException;

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
