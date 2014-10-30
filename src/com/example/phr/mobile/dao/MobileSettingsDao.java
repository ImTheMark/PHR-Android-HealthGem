package com.example.phr.mobile.dao;

public interface MobileSettingsDao {
	
	public void initializeSettings();
	
	public void setBloodPressureNotificationOn();
	
	public void setBloodPressureNotificationOff();
	
	public Boolean getBloodPressureNotificationSetting();
	
	public void setBloodSugarNotificationOn();
	
	public void setBloodSugarNotificationOff();
	
	public Boolean getBloodSugarNotificationSetting();
	

}
