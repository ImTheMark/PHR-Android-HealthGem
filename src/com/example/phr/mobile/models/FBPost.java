package com.example.phr.mobile.models;

import java.io.Serializable;
import java.util.Date;

public class FBPost implements Serializable {

	public String id;
	public String status;
	public Date datetime;
	public FBPostType postType;
	public PHRImage image;
	public String[] extractedWords;

	public FBPost(String id) {
		super();
		this.id = id;
	}

	public FBPost(String id, String status, Date datetime,
			FBPostType postType, PHRImage image, String[] extractedWords) {
		super();
		this.id = id;
		this.status = status;
		this.datetime = datetime;
		this.postType = postType;
		this.image = image;
		this.extractedWords = extractedWords;
	}

	public FBPost(String status, Date datetime, FBPostType postType,
			PHRImage image, String[] extractedWords) {
		super();
		this.status = status;
		this.datetime = datetime;
		this.postType = postType;
		this.image = image;
		this.extractedWords = extractedWords;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public FBPostType getPostType() {
		return postType;
	}

	public void setPostType(FBPostType postType) {
		this.postType = postType;
	}

	public PHRImage getImage() {
		return image;
	}

	public void setImage(PHRImage image) {
		this.image = image;
	}

	public String[] getExtractedWords() {
		return extractedWords;
	}

	public void setExtractedWords(String[] extractedWords) {
		this.extractedWords = extractedWords;
	}

}
