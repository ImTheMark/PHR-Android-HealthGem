package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class UnverifiedActivityEntry extends TrackerEntry {

	String activityName;
	int durationInSeconds;
	int calorieBurnedPerHour;

	public UnverifiedActivityEntry(Integer entryID, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			String activityName, int durationInSeconds, int calorieBurnedPerHour) {
		super(entryID, facebookID, timestamp, status, image);
		this.activityName = activityName;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
	}

	public UnverifiedActivityEntry(String facebookID, Timestamp timestamp,
			String status, PHRImage image, String activityName,
			int durationInSeconds, int calorieBurnedPerHour) {
		super(facebookID, timestamp, status, image);
		this.activityName = activityName;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	public int getCalorieBurnedPerHour() {
		return calorieBurnedPerHour;
	}

	public void setCalorieBurnedPerHour(int calorieBurnedPerHour) {
		this.calorieBurnedPerHour = calorieBurnedPerHour;
	}

}
