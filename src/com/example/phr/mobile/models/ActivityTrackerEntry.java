package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActivityTrackerEntry extends TrackerEntry implements Serializable {

	Activity activity;
	double caloriesBurnedPerHour;
	int durationInSeconds;

	public ActivityTrackerEntry(Integer entryID, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, double caloriesBurnedPerHour,
			int durationInSeconds) {
		super(entryID, facebookID, timestamp, status, image);
		this.activity = activity;
		this.caloriesBurnedPerHour = caloriesBurnedPerHour;
	}

	public ActivityTrackerEntry(Integer entryID, Timestamp timestamp,
			String status, PHRImage image, Activity activity,
			double caloriesBurnedPerHour, int durationInSeconds) {
		super(entryID, timestamp, status, image);
		this.activity = activity;
		this.caloriesBurnedPerHour = caloriesBurnedPerHour;
	}

	public ActivityTrackerEntry(String facebookID, Timestamp timestamp,
			String status, PHRImage image, Activity activity,
			double caloriesBurnedPerHour, int durationInSeconds) {
		super(facebookID, timestamp, status, image);
		this.activity = activity;
		this.caloriesBurnedPerHour = caloriesBurnedPerHour;
	}

	public ActivityTrackerEntry(Timestamp timestamp, String status,
			PHRImage image, Activity activity,
			double caloriesBurnedPerHour, int durationInSeconds) {
		super(timestamp, status, image);
		this.activity = activity;
		this.caloriesBurnedPerHour = caloriesBurnedPerHour;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
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
