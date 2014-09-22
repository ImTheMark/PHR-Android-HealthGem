package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class Note extends TrackerEntry {

	String note;

	public Note(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, String note) {
		super(entryID, fbPost, timestamp, status, image);
		this.note = note;
	}

	public Note(Integer entryID, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(entryID, timestamp, status, image);
		this.note = note;
	}

	public Note(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(fbPost, timestamp, status, image);
		this.note = note;
	}

	public Note(Timestamp timestamp, String status, PHRImage image,
			 String note) {
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
