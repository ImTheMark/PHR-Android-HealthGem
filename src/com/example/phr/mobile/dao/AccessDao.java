package com.example.phr.mobile.dao;

import com.example.phr.model.AccessToken;

public interface AccessDao {

	public AccessToken getAccessToken();

	public void setAccessToken(AccessToken accessToken);
}
