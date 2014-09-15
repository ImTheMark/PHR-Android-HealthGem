package com.example.phr.service;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.MobileBloodPressure;

public interface BloodPressureService {

	public void addBloodPressure(MobileBloodPressure bloodPressure)
			throws ServiceException, OutdatedAccessTokenException;

}
