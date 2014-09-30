package com.example.phr.mobile.dao;

import com.example.phr.model.AccessToken;

public interface MobileAccessDao {

	public AccessToken getAccessToken();

	public void setAccessToken(AccessToken accessToken);
}
