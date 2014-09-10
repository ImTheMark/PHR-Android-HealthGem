package com.example.phr.service;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.model.BloodPressure;

public interface BloodPressureService {

	public void addBloodPressure(BloodPressure bloodPressure)
			throws ServiceException, OutdatedAccessTokenException;

}
