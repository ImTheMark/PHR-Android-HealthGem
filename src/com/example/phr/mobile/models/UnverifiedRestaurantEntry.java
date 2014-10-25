package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class UnverifiedRestaurantEntry extends TrackerEntry {

	String restaurantName;

	public UnverifiedRestaurantEntry(Integer entryID, 
			String facebookID, Timestamp timestamp, String status,
			PHRImage image, String restaurantName) {
		super(entryID, facebookID, timestamp, status, image);
		this.restaurantName = restaurantName;
	}

	public UnverifiedRestaurantEntry(User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			String restaurantName) {
		super(user, facebookID, timestamp, status, image);
		this.restaurantName = restaurantName;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

}
