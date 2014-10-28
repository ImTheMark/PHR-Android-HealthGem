package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class TrackerEntry implements Serializable {

	Integer entryID;
	String facebookID;
	Timestamp timestamp;
	String status;
	PHRImage image;

	public TrackerEntry(Integer entryID, String facebookID,
			Timestamp timestamp, String status, PHRImage image) {
		super();
		this.entryID = entryID;
		this.facebookID = facebookID;
		this.timestamp = timestamp;
		this.status = status;
		this.image = image;
	}

	public TrackerEntry(String facebookID, Timestamp timestamp, String status,
			PHRImage image) {
		super();
		this.facebookID = facebookID;
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

	public String getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
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
