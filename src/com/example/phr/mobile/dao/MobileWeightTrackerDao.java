package com.example.phr.mobile.dao;

import java.util.List;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.Weight;

public interface MobileWeightTrackerDao extends MobileTrackerDao<Weight> {

	public List<List<Weight>> getAllGroupedByDate() throws DataAccessException;

}
