package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class ActivityTrackerEntry extends TrackerEntry {

	Activity activity;
	double calorisBurnedPerHour;

	public ActivityTrackerEntry(Integer entryID, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, double calorisBurnedPerHour) {
		super(entryID, fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorisBurnedPerHour = calorisBurnedPerHour;
	}

	public ActivityTrackerEntry(Integer entryID, 
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, double calorisBurnedPerHour) {
		super(entryID, timestamp, status, image);
		this.activity = activity;
		this.calorisBurnedPerHour = calorisBurnedPerHour;
	}

	public ActivityTrackerEntry(FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, Activity activity,
			double calorisBurnedPerHour) {
		super(fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorisBurnedPerHour = calorisBurnedPerHour;
	}

	public ActivityTrackerEntry(Timestamp timestamp, String status,
			PHRImage image, Activity activity, double calorisBurnedPerHour) {
		super(timestamp, status, image);
		this.activity = activity;
		this.calorisBurnedPerHour = calorisBurnedPerHour;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public double getCalorisBurnedPerHour() {
		return calorisBurnedPerHour;
	}

	public void setCalorisBurnedPerHour(double calorisBurnedPerHour) {
		this.calorisBurnedPerHour = calorisBurnedPerHour;
	}
	
	
	
	
}
