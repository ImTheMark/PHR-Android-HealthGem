package com.example.phr.mobile.dao;

import java.sql.Timestamp;
import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.GroupedFood;

public interface MobileFoodTrackerDao extends
		MobileTrackerDao<FoodTrackerEntry> {

	public List<List<FoodTrackerEntry>> getAllGroupedByDate()
			throws DataAccessException;

	public List<FoodTrackerEntry> getAllFromDate(Timestamp timestamp)
			throws DataAccessException;

	List<GroupedFood> getAllGroupedByDateCalculated()
			throws DataAccessException;

}
