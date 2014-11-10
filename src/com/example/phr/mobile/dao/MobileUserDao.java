package com.example.phr.mobile.dao;

import android.app.Activity;
import android.content.Context;

import com.example.phr.mobile.models.User;

public interface MobileUserDao {
	
	public void loginUser(User user);
	
	public void logoutUser();
	
	public void logoutUser(Context context);
	
	public void clearRegisterInformation();
	
	public void registerUser();
	
	public void saveUser(User user);
	
	public void editUser(User user);

	public User getUser();

}
