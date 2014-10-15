package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class CheckUp extends TrackerEntry implements Serializable {

	String purpose;
	String doctorsName;
	String notes;

	public CheckUp(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, String purpose, String doctorsName,
			String notes) {
		super(entryID, fbPost, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.notes = notes;
	}

	public CheckUp(Integer entryID, Timestamp timestamp, String status,
			PHRImage image, String purpose, String doctorsName, String notes) {
		super(entryID, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.notes = notes;
	}

	public CheckUp(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, String purpose, String doctorsName, String notes) {
		super(fbPost, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.notes = notes;
	}

	public CheckUp(Timestamp timestamp, String status, PHRImage image,
			String purpose, String doctorsName, String notes) {
		super(timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.notes = notes;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDoctorsName() {
		return doctorsName;
	}

	public void setDoctorsName(String doctorsName) {
		this.doctorsName = doctorsName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
