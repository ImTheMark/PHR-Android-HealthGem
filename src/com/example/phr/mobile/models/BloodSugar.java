package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class BloodSugar extends TrackerEntry {

	int systolic;
	int diastolic;

	public BloodSugar(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, int systolic, int diastolic) {
		super(entryID, fbPost, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodSugar(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, int systolic, int diastolic) {
		super(fbPost, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodSugar(Integer entryID, Timestamp timestamp, String status,
			PHRImage image, int systolic, int diastolic) {
		super(entryID, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodSugar(Timestamp timestamp, String status, PHRImage image,
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
