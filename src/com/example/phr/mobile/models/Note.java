package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Note extends TrackerEntry implements Serializable {

	String note;

	public Note(Integer entryID, String facebookID, Timestamp timestamp,
			String status, PHRImage image, String note) {
		super(entryID, facebookID, timestamp, status, image);
		this.note = note;
	}

	public Note(Integer entryID, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(entryID, timestamp, status, image);
		this.note = note;
	}

	public Note(String facebookID, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(facebookID, timestamp, status, image);
		this.note = note;
	}

	public Note(Timestamp timestamp, String status, PHRImage image, String note) {
		super(timestamp, status, image);
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
