package com.example.phr.web.dao;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.MobileBloodPressure;

public interface WebBloodPressureDao {

	void addBloodPressure(MobileBloodPressure bloodPressure)
			throws WebServerException, OutdatedAccessTokenException;

}
