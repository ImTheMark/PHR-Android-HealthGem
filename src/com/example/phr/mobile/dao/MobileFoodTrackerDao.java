package com.example.phr.mobile.dao;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.FoodTrackerEntry;

public interface MobileFoodTrackerDao extends
		MobileTrackerDao<FoodTrackerEntry> {

	public List<List<FoodTrackerEntry>> getAllGroupedByDate() throws DataAccessException;

}
