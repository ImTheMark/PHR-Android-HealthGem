package com.example.model;

import java.sql.Date;

public abstract class TrackerEntry {

	Integer entryID;
	Integer userID;
	Integer fbPostID;
	Date dateAdded;
	String status;
	String encodedImage;

	public TrackerEntry(Integer entryID, Integer userID, Integer fbPostID,
			Date dateAdded, String status, String encodedImage) {
		super();
		this.entryID = entryID;
		this.userID = userID;
		this.fbPostID = fbPostID;
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public TrackerEntry(Date dateAdded, String status, String encodedImage) {
		super();
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
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

}
