package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActivityTrackerEntry extends TrackerEntry implements Serializable {

	ActivitySingle activity;
	double caloriesBurnedPerHour;
	int durationInSeconds;

	public ActivityTrackerEntry(Integer entryID, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			ActivitySingle activity, double caloriesBurnedPerHour,
			int durationInSeconds) {
		super(entryID, facebookID, timestamp, status, image);
		this.activity = activity;
		this.caloriesBurnedPerHour = caloriesBurnedPerHour;
	}

	public ActivityTrackerEntry(Integer entryID, Timestamp timestamp,
			String status, PHRImage image, ActivitySingle activity,
			double caloriesBurnedPerHour, int durationInSeconds) {
		super(entryID, timestamp, status, image);
		this.activity = activity;
		this.caloriesBurnedPerHour = caloriesBurnedPerHour;
	}

	public ActivityTrackerEntry(String facebookID, Timestamp timestamp,
			String status, PHRImage image, ActivitySingle activity,
			double caloriesBurnedPerHour, int durationInSeconds) {
		super(facebookID, timestamp, status, image);
		this.activity = activity;
		this.caloriesBurnedPerHour = caloriesBurnedPerHour;
	}

	public ActivityTrackerEntry(Timestamp timestamp, String status,
			PHRImage image, ActivitySingle activity,
			double caloriesBurnedPerHour, int durationInSeconds) {
		super(timestamp, status, image);
		this.activity = activity;
		this.caloriesBurnedPerHour = caloriesBurnedPerHour;
	}

	public ActivitySingle getActivity() {
		return activity;
	}

	public void setActivity(ActivitySingle activity) {
		this.activity = activity;
	}

	public double getCaloriesBurnedPerHour() {
		return caloriesBurnedPerHour;
	}

	public void setCaloriesBurnedPerHour(double caloriesBurnedPerHour) {
		this.caloriesBurnedPerHour = caloriesBurnedPerHour;
	}

	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

}
