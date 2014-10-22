package com.example.phr.mobile.dao;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.ActivityTrackerEntry;

public interface MobileActivityTrackerDao extends
		MobileTrackerDao<ActivityTrackerEntry> {

	public List<List<ActivityTrackerEntry>> getAllGroupedByDate() throws DataAccessException;
}
