package com.example.phr.model;

import java.sql.Date;

public class BloodPressure extends TrackerEntry {

	int systolic;
	int diastolic;

	public BloodPressure(Integer entryID, Integer userID, Integer fbPostID,
			Date dateAdded, String status, String encodedImage, int systolic,
			int diastolic) {
		super(entryID, userID, fbPostID, dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Date dateAdded, String status, String encodedImage,
			int systolic, int diastolic) {
		super(dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public int getSystolic() {
		return systolic;
	}

	public void setSystolic(int systolic) {
		this.systolic = systolic;
	}

	public int getDiastolic() {
		return diastolic;
	}

	public void setDiastolic(int diastolic) {
		this.diastolic = diastolic;
	}

}