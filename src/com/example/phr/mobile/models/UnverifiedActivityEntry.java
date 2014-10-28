package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class UnverifiedActivityEntry extends TrackerEntry {

	ActivitySingle activity;
	int durationInSeconds;
	Double calorieBurnedPerHour;
	String extractedWord;

	public UnverifiedActivityEntry(Integer entryID, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			ActivitySingle activity, int durationInSeconds,
			Double calorieBurnedPerHour, String extractedWord) {
		super(entryID, facebookID, timestamp, status, image);
		this.activity = activity;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
		this.extractedWord = extractedWord;
	}

	public UnverifiedActivityEntry(String facebookID, Timestamp timestamp,
			String status, PHRImage image, ActivitySingle activity,
			int durationInSeconds, Double calorieBurnedPerHour,
			String extractedWord) {
		super(facebookID, timestamp, status, image);
		this.activity = activity;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
		this.extractedWord = extractedWord;
	}

	public ActivitySingle getActivity() {
		return activity;
	}

	public void setActivity(ActivitySingle activity) {
		this.activity = activity;
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

	public String getExtractedWord() {
		return extractedWord;
	}

	public void setExtractedWord(String extractedWord) {
		this.extractedWord = extractedWord;
	}

}
