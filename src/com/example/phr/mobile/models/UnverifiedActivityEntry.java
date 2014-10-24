package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class UnverifiedActivityEntry {

	Integer entryID;
	Timestamp timestamp;
	String activityName;
	int durationInSeconds;
	Double calorieBurnedPerHour;
	String status;
	PHRImage image;
	String fbPostID;

	public UnverifiedActivityEntry(Integer entryID, Timestamp timestamp,
			String activityName, int durationInSeconds,
			Double calorieBurnedPerHour, String status, PHRImage image,
			String fbPostID) {
		super();
		this.entryID = entryID;
		this.timestamp = timestamp;
		this.activityName = activityName;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
		this.status = status;
		this.image = image;
		this.fbPostID = fbPostID;
	}

	public UnverifiedActivityEntry(Timestamp timestamp, String activityName,
			int durationInSeconds, Double calorieBurnedPerHour, String status,
			PHRImage image, String fbPostID) {
		super();
		this.timestamp = timestamp;
		this.activityName = activityName;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
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
