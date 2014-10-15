package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;

import com.example.phr.model.User;

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

	public TrackerEntry(User user, FBPost fbPost2, Timestamp timestamp2,
			String status2, PHRImage image2) {
		// TODO Auto-generated constructor stub
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
