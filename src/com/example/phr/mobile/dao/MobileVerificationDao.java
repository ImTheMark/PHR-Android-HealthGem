package com.example.phr.mobile.dao;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;

public interface MobileVerificationDao {
	
	public void addUnverifiedFoodPost(UnverifiedFoodEntry entry) throws DataAccessException;
	
	public void addUnverifiedRestaurantPost(UnverifiedRestaurantEntry entry) throws DataAccessException;
	
	public void addUnverifiedActivityPost(UnverifiedActivityEntry entry) throws DataAccessException;
	
	public void addUnverifiedSportEstablishmentPost(UnverifiedSportsEstablishmentEntry entry) throws DataAccessException;

	
	
	public void deleteUnverifiedFoodPost(UnverifiedFoodEntry entry);
	
	public void deleteUnverifiedRestaurantPost(UnverifiedRestaurantEntry entry);
	
	public void deleteUnverifiedActivityPost(UnverifiedActivityEntry entry);
	
	public void deleteUnverifiedSportEstablishmentPost(UnverifiedSportsEstablishmentEntry entry);
	
	

	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts() throws DataAccessException;

	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts() throws DataAccessException;

	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts() throws DataAccessException;

	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts() throws DataAccessException;
	
	
	public void emptyVerificationDatabase();
	
	
	public List<TrackerEntry> getAllUnverifiedPosts() throws DataAccessException;
	
	public int getUnverifiedPostsCount();
	
	
	
	public void addFoodListToTempDB(List<Food> foodList);
	
	public void addActivityListToTempDB(List<Activity> activityList);
	
	public List<Food> getFoodListFromTempDB();
	
	public List<Activity> getActivityListFromTempDB();
	
	
	
	public void storeEncodedImage(String encodedImage);
	
	public String getImageFileName();

}
