package phr.models;

import java.sql.Timestamp;
import java.util.List;

public class UnverifiedSportsEstablishmentEntry extends TrackerEntry {

	String extractedWord;
	SportEstablishment sportEstablishment;
	List<ActivitySingle> activities;

	public UnverifiedSportsEstablishmentEntry(Integer entryID,
			String facebookID, Timestamp timestamp, String status,
			PHRImage image, String extractedWord,
			SportEstablishment sportEstablishment,
			List<ActivitySingle> activities) {
		super(entryID, facebookID, timestamp, status, image);
		this.extractedWord = extractedWord;
		this.sportEstablishment = sportEstablishment;
		this.activities = activities;
	}

	public UnverifiedSportsEstablishmentEntry(String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			String extractedWord, SportEstablishment sportEstablishment,
			List<ActivitySingle> activities) {
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

	public List<ActivitySingle> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivitySingle> activities) {
		this.activities = activities;
	}

}
