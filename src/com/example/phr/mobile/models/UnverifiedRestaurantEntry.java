package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class UnverifiedRestaurantEntry {

	Integer entryID;
	Timestamp timestamp;
	String restaurantName;
	String status;
	PHRImage image;
	String fbPostID;

	public UnverifiedRestaurantEntry(Integer entryID, Timestamp timestamp,
			String restaurantName, String status, PHRImage image,
			String fbPostID) {
		super();
		this.entryID = entryID;
		this.timestamp = timestamp;
		this.restaurantName = restaurantName;
		this.status = status;
		this.image = image;
		this.fbPostID = fbPostID;
	}

	public UnverifiedRestaurantEntry(Timestamp timestamp,
			String restaurantName, String status, PHRImage image,
			String fbPostID) {
		super();
		this.timestamp = timestamp;
		this.restaurantName = restaurantName;
		this.status = status;
		this.image = image;
		this.fbPostID = fbPostID;
	}

	public Integer getEntryID() {
		return entryID;
	}

	public void setEntryID(Integer entryID) {
		this.entryID = entryID;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PHRImage getImage() {
		return image;
	}

	public void setImage(PHRImage image) {
		this.image = image;
	}

	public String getFbPostID() {
		return fbPostID;
	}

	public void setFbPostID(String fbPostID) {
		this.fbPostID = fbPostID;
	}

}
