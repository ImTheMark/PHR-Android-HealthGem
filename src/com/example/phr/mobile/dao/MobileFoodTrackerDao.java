package com.example.phr.mobile.dao;

import java.sql.Timestamp;
import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.FoodTrackerEntry;

public interface MobileFoodTrackerDao extends
		MobileTrackerDao<FoodTrackerEntry> {

	public List<List<FoodTrackerEntry>> getAllGroupedByDate() throws DataAccessException;
	
	public List<FoodTrackerEntry> getAllFromDate(Timestamp timestamp) throws DataAccessException;

}
