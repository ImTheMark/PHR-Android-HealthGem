package com.example.phr.mobile.dao;

import java.util.ArrayList;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.ActivityTrackerEntry;

public interface MobileActivityDao extends MobileTrackerDao<ActivityTrackerEntry> {
	
	public void addActivityListEntry(Activity activity);
	
	public ArrayList<Activity> getAllActivity() throws DataAccessException;
	
}
