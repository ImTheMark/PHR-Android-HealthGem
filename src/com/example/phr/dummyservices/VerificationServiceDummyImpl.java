package com.example.phr.dummyservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.Restaurant;
import com.example.phr.mobile.models.SportEstablishment;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.service.VerificationService;

public class VerificationServiceDummyImpl implements VerificationService {

	@Override
	public void addFoodListToTemporaryDatabase(List<Food> foodList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActivityListToTemporaryDatabase(List<Activity> activityList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Food> getFoodList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getActivityList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateListOfUnverifiedPosts() throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TrackerEntry> getAll() throws ServiceException,
			OutdatedAccessTokenException {
		List<TrackerEntry> list = new ArrayList<TrackerEntry>();

		list.addAll(getAllUnverifiedActivityPosts());
		list.addAll(getAllUnverifiedFoodPosts());
		list.addAll(getAllUnverifiedRestaurantPosts());
		list.addAll(getAllUnverifiedSportsEstablishmentPosts());
		
		return list;
	}

	@Override
	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts()
			throws ServiceException, OutdatedAccessTokenException {
		List<UnverifiedFoodEntry> list = new ArrayList<UnverifiedFoodEntry>();
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getDate());
		
		Food food = new Food(1242242);
		food.setName("Chocolate cake");
		
		UnverifiedFoodEntry e1 = new UnverifiedFoodEntry("1234567890", timestamp,
				"I ate a chocolate cake", null, food, 1.0,
				"chocolate cake");
		

		food.setName("Cheesecake");
		
		UnverifiedFoodEntry e2 = new UnverifiedFoodEntry("1234567890", timestamp,
				"Cheesecake for desert. YUM YUM YUM!", null, food, 1.0,
				"Cheesecake");
		

		food.setName("Fried Chicken");
		
		UnverifiedFoodEntry e3 = new UnverifiedFoodEntry("1234567890", timestamp,
				"Dinner today: chicken! Yaaaay", null, food, 1.0,
				"chicken");
		
		list.add(e1);
		list.add(e2);
		list.add(e3);
		
		return list;
	}

	@Override
	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts()
			throws ServiceException, OutdatedAccessTokenException {
		List<UnverifiedActivityEntry> list = new ArrayList<UnverifiedActivityEntry>();
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getDate());
		
		Activity activity = new Activity("Jogging", 12.0);
		
		UnverifiedActivityEntry e1 = new UnverifiedActivityEntry("1234567890", timestamp,
				"Good morning #jogging", null, activity,
				2832, 14.0,
				"Jogging");
		
		
		
		Activity activity2 = new Activity("Swimming", 54.0);
		
		UnverifiedActivityEntry e2 = new UnverifiedActivityEntry("1234567890", timestamp,
				"Having some fun swimming with my friends", null, activity2,
				2235832, 14.0,
				"Swimming");
		
		
		
		Activity activity3 = new Activity("Yoga", 23.0);
		
		UnverifiedActivityEntry e3 = new UnverifiedActivityEntry("1234567890", timestamp,
				"Yoga with mommy :)", null, activity3,
				243535, 14.0,
				"Yoga");
		
		list.add(e1);
		list.add(e2);
		list.add(e3);
		
		return list;
	}

	@Override
	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts()
			throws ServiceException, OutdatedAccessTokenException {
		List<UnverifiedRestaurantEntry> list = new ArrayList<UnverifiedRestaurantEntry>();
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getDate());
		
		Restaurant restaurant = new Restaurant(2343423, "KFC");
		
		UnverifiedRestaurantEntry e1 = new UnverifiedRestaurantEntry("2142323432", timestamp,
				"Here at KFC!", null, "KFC",
				restaurant, null);
		

		
		Restaurant restaurant2 = new Restaurant(2343423, "Mang Inasal");
		
		UnverifiedRestaurantEntry e2 = new UnverifiedRestaurantEntry("2142323432", timestamp,
				"Mang Inasal is so delicious. I can't stop eating it.", null, "Mang Inasal",
				restaurant2, null);
		

		
		Restaurant restaurant3 = new Restaurant(2343423, "Starbucks");
		
		UnverifiedRestaurantEntry e3 = new UnverifiedRestaurantEntry("2142323432", timestamp,
				"Starbucks today!", null, "Starbucks",
				restaurant3, null);
		
		list.add(e1);
		list.add(e2);
		list.add(e3);
		
		
		return list;
	}

	@Override
	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts()
			throws ServiceException, OutdatedAccessTokenException {
		List<UnverifiedSportsEstablishmentEntry> list = new ArrayList<UnverifiedSportsEstablishmentEntry>();
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getDate());
		
		
		SportEstablishment sportEstablishment1 = new SportEstablishment(34234, "Gold's Gym");

		UnverifiedSportsEstablishmentEntry e1 = new UnverifiedSportsEstablishmentEntry("34346436643",
				timestamp, "Workout at Gold's Gym", null,
				"Gold's Gym", sportEstablishment1,
				null);
		
		
		SportEstablishment sportEstablishment2 = new SportEstablishment(34234, "Fitness First");

		UnverifiedSportsEstablishmentEntry e2 = new UnverifiedSportsEstablishmentEntry("34346436643",
				timestamp, "Here at Fitness First with friends", null,
				"Fitness First", sportEstablishment2,
				null);
		
		list.add(e1);
		list.add(e2);
		
		return list;
	}

	@Override
	public void delete(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeImage(String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getImageFileName() {
		// TODO Auto-generated method stub
		return null;
	}

}
