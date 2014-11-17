package com.example.phr.service;

import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Weight;

public interface WeightTrackerService extends TrackerService<Weight> {

	public String getFeedback() throws ServiceException;

}
