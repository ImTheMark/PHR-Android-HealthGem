package com.example.phr.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public abstract class old_TrackerEntry {

	Integer entryID;
	Integer userID;
	Integer fbPostID;
	Date dateAdded;
	String status;
	String encodedImage;
	String date;
	String time;

	public old_TrackerEntry(Integer entryID, Integer userID, Integer fbPostID,
			Date dateAdded, String status, String encodedImage) {
		super();
		this.entryID = entryID;
		this.userID = userID;
		this.fbPostID = fbPostID;
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
		parseDateTime();
	}

	public old_TrackerEntry(Date dateAdded, String status, String encodedImage) {
		super();
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
		parseDateTime();
	}

	private void parseDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		date = formatter.format(dateAdded);
		formatter = new SimpleDateFormat("HH:mm:ss");
		time = formatter.format(dateAdded);
	}

	public Integer getEntryID() {
		return entryID;
	}

	public void setEntryID(Integer entryID) {
		this.entryID = entryID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getFbPostID() {
		return fbPostID;
	}

	public void setFbPostID(Integer fbPostID) {
		this.fbPostID = fbPostID;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEncodedImage() {
		return encodedImage;
	}

	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

}
