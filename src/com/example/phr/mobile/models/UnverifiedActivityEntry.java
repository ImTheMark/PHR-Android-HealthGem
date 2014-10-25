package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class UnverifiedActivityEntry extends TrackerEntry {

	String activityName;
	int durationInSeconds;
	Double calorieBurnedPerHour;

	public UnverifiedActivityEntry(Integer entryID,
			String facebookID, Timestamp timestamp, String status,
			PHRImage image, String activityName, int durationInSeconds,
			Double calorieBurnedPerHour) {
		super(entryID, facebookID, timestamp, status, image);
		this.activityName = activityName;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
	}

	public UnverifiedActivityEntry(User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			String activityName, int durationInSeconds,
			Double calorieBurnedPerHour) {
		super(user, facebookID, timestamp, status, image);
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

	public Double getCalorieBurnedPerHour() {
		return calorieBurnedPerHour;
	}

	public void setCalorieBurnedPerHour(Double calorieBurnedPerHour) {
		this.calorieBurnedPerHour = calorieBurnedPerHour;
	}

}
