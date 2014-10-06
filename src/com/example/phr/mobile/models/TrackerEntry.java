package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class TrackerEntry implements Serializable {

	Integer entryID;
	FBPost fbPost;
	Timestamp timestamp;
	String status;
	PHRImage image;

	public TrackerEntry(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image) {
		super();
		this.entryID = entryID;
		this.fbPost = fbPost;
		this.timestamp = timestamp;
		this.status = status;
		this.image = image;
	}

	public TrackerEntry(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image) {
		super();
		this.fbPost = fbPost;
		this.timestamp = timestamp;
		this.status = status;
		this.image = image;
	}

	public TrackerEntry(Integer entryID, Timestamp timestamp, String status,
			PHRImage image) {
		super();
		this.entryID = entryID;
		this.timestamp = timestamp;
		this.status = status;
		this.image = image;
	}

	public TrackerEntry(Timestamp timestamp, String status, PHRImage image) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.image = image;
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

	public PHRImage getImage() {
		return image;
	}

	public void setImage(PHRImage image) {
		this.image = image;
	}
}
