package com.example.phr.mobile.dao;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.ActivitySingle;
import com.example.phr.mobile.models.ActivityTrackerEntry;

public interface MobileActivityDao extends MobileTrackerDao<ActivityTrackerEntry> {
	
	public void addActivityListEntry(SQLiteDatabase db, ActivitySingle activity);
	
	public ArrayList<ActivitySingle> getAllActivityListEntry() throws DataAccessException;
	
	public Boolean activityListEntryExists(SQLiteDatabase db, Integer activityID) throws DataAccessException;
	
	public ActivitySingle getActivityListEntry(SQLiteDatabase db, Integer activityID) throws DataAccessException;
	
}
