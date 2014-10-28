package com.example.phr.mobile.models;

import java.sql.Timestamp;
import java.util.List;

public class UnverifiedSportsEstablishmentEntry extends TrackerEntry {

	String extractedWord;
	SportEstablishment sportEstablishment;
	List<Activity> activities;

	public UnverifiedSportsEstablishmentEntry(Integer entryID,
			String facebookID, Timestamp timestamp, String status,
			PHRImage image, String extractedWord,
			SportEstablishment sportEstablishment,
			List<Activity> activities) {
		super(entryID, facebookID, timestamp, status, image);
		this.extractedWord = extractedWord;
		this.sportEstablishment = sportEstablishment;
		this.activities = activities;
	}

	public UnverifiedSportsEstablishmentEntry(String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			String extractedWord, SportEstablishment sportEstablishment,
			List<Activity> activities) {
		super(facebookID, timestamp, status, image);
		this.extractedWord = extractedWord;
		this.sportEstablishment = sportEstablishment;
		this.activities = activities;
	}

	public String getExtractedWord() {
		return extractedWord;
	}

	public void setExtractedWord(String extractedWord) {
		this.extractedWord = extractedWord;
	}

	public SportEstablishment getSportEstablishment() {
		return sportEstablishment;
	}

	public void setSportEstablishment(SportEstablishment sportEstablishment) {
		this.sportEstablishment = sportEstablishment;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

}
