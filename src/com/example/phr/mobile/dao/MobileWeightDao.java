package com.example.phr.mobile.dao;

import java.util.List;
import java.util.Map;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.Weight;

public interface MobileWeightDao extends MobileTrackerDao<Weight> {

	public Map<String, List<Weight>> getAllGroupedByDate() throws DataAccessException;

}
