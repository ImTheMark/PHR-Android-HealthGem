package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class BloodPressure extends TrackerEntry {

	int systolic;
	int diastolic;

	public BloodPressure(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, int systolic, int diastolic) {
		super(entryID, fbPost, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, int systolic, int diastolic) {
		super(fbPost, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Integer entryID, Timestamp timestamp, String status,
			PHRImage image, int systolic, int diastolic) {
		super(entryID, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Timestamp timestamp, String status, PHRImage image,
			int systolic, int diastolic) {
		super(timestamp, status, image);
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
