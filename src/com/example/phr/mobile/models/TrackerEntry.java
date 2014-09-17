package com.example.phr.mobile.models;

import java.sql.Timestamp;

public abstract class TrackerEntry {

	Integer entryID;
	FBPost fbPost;
	Timestamp timestamp;
	String status;
	String encodedImage;

	public TrackerEntry(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, String encodedImage) {
		super();
		this.entryID = entryID;
		this.fbPost = fbPost;
		this.timestamp = timestamp;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public TrackerEntry(FBPost fbPost, Timestamp timestamp, String status,
			String encodedImage) {
		super();
		this.fbPost = fbPost;
		this.timestamp = timestamp;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public TrackerEntry(Integer entryID, Timestamp timestamp, String status,
			String encodedImage) {
		super();
		this.entryID = entryID;
		this.timestamp = timestamp;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public TrackerEntry(Timestamp timestamp, String status, String encodedImage) {
		super();
		this.timestamp = timestamp;
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
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
