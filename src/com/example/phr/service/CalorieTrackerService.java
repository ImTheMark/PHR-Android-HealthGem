package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.CalorieTrackerEntry;

public interface CalorieTrackerService {
	
	public List<CalorieTrackerEntry> getAll() throws ServiceException;

}
