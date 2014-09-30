package com.example.phr.mobile.daoimpl;

import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileAccessDao;
import com.example.phr.model.AccessToken;

public class MobileAccessDaoImpl implements MobileAccessDao {

	@Override
	public AccessToken getAccessToken() {
		AccessToken token = DatabaseHandler.getDBHandler().getAccessToken();
		return token;
	}

	@Override
	public void setAccessToken(AccessToken accessToken) {
		DatabaseHandler.getDBHandler().setAccessToken(accessToken);
	}

}
