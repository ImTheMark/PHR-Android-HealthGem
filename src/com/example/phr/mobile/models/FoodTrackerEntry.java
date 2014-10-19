package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class FoodTrackerEntry extends TrackerEntry implements Serializable {

	Food food;
	double servingCount;

	public FoodTrackerEntry(Integer entryID, String facebookID,
			Timestamp timestamp, String status, PHRImage image, Food food,
			double servingCount) {
		super(entryID, facebookID, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}

	public FoodTrackerEntry(Integer entryID, Timestamp timestamp,
			String status, PHRImage image, Food food, double servingCount) {
		super(entryID, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}

	public FoodTrackerEntry(String facebookID, Timestamp timestamp,
			String status, PHRImage image, Food food, double servingCount) {
		super(facebookID, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}

	public FoodTrackerEntry(Timestamp timestamp, String status, PHRImage image,
			Food food, double servingCount) {
		super(timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public double getServingCount() {
		return servingCount;
	}

	public void setServingCount(double servingCount) {
		this.servingCount = servingCount;
	}
}
