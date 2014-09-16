package com.example.phr.mobile.models;

import java.sql.Date;

import com.example.phr.web.models.FBPost;

public abstract class TrackerEntry {

	Integer entryID;
	FBPost fbPost;
	Date dateAdded;
	String status;
	String encodedImage;

	public TrackerEntry(Integer entryID, FBPost fbPost, Date dateAdded,
			String status, String encodedImage) {
		super();
		this.entryID = entryID;
		this.fbPost = fbPost;
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public TrackerEntry(FBPost fbPost, Date dateAdded, String status,
			String encodedImage) {
		super();
		this.fbPost = fbPost;
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public TrackerEntry(Integer entryID, Date dateAdded, String status,
			String encodedImage) {
		super();
		this.entryID = entryID;
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

	public FBPost getFbPost() {
		return fbPost;
	}

	public void setFbPost(FBPost fbPost) {
		this.fbPost = fbPost;
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
