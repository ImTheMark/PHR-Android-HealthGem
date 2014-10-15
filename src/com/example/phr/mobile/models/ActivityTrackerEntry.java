package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class ActivityTrackerEntry extends TrackerEntry {

	ActivitySingle activity;
	double caloriesBurnedPerHour;
	int durationInSeconds;

	public ActivityTrackerEntry(Integer entryID, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image,
			ActivitySingle activity, double caloriesBurnedPerHour,
			int durationInSeconds) {
		super(entryID, fbPost, timestamp, status, image);
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

	public ActivityTrackerEntry(FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, ActivitySingle activity,
			double caloriesBurnedPerHour, int durationInSeconds) {
		super(fbPost, timestamp, status, image);
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
