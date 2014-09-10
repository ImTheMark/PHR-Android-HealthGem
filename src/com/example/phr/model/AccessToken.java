package com.example.phr.model;

public class AccessToken {

	String accessToken;
	String userName;

	public AccessToken() {
	}

	public AccessToken(String accessToken, String userName) {
		super();
		this.accessToken = accessToken;
		this.userName = userName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
