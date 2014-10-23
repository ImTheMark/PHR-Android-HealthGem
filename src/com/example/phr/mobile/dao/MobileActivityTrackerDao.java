package com.example.phr.mobile.dao;

import java.sql.Timestamp;
import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.GroupedActivity;

public interface MobileActivityTrackerDao extends
		MobileTrackerDao<ActivityTrackerEntry> {

	public List<List<ActivityTrackerEntry>> getAllGroupedByDate()
			throws DataAccessException;

	List<ActivityTrackerEntry> getAllFromDate(Timestamp date)
			throws DataAccessException;

	GroupedActivity getFromDateCalculated(Timestamp date)
			throws DataAccessException;
}
