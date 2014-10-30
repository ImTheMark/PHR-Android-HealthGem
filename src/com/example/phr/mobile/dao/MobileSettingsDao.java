package com.example.phr.mobile.dao;

public interface MobileSettingsDao {
	
	public void initializeSettings();
	
	public void setBloodPressureNotificationOn();
	
	public void setBloodPressureNotificationOff();
	
	public Boolean getBloodPressureNotificationSetting();
	
	public void setBloodSugarNotificationOn();
	
	public void setBloodSugarNotificationOff();
	
	public Boolean getBloodSugarNotificationSetting();
	
	public void setWeightToKilograms();
	
	public void setWeightToPounds();
	
	public Boolean isWeightSettingInPounds();
	
	public void setHeightToFeet();
	
	public void setHeightToCentimeters();
	
	public Boolean isHeightSettingInFeet();
	

}
