package com.example.phr.serviceimpl;

import android.content.Context;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.MobileBloodPressure;
import com.example.phr.service.BloodPressureService;
import com.example.phr.web.dao.BloodPressureDao;
import com.example.phr.web.daoimpl.BloodPressureDaoImpl;

public class BloodPressureServiceImpl implements BloodPressureService {

	BloodPressureDao bloodPressureDao;

	public BloodPressureServiceImpl() {
		bloodPressureDao = new BloodPressureDaoImpl();
	}

	@Override
	public void addBloodPressure(MobileBloodPressure bloodPressure)
			throws ServiceException, OutdatedAccessTokenException {
		try {
			bloodPressureDao.addBloodPressure(bloodPressure);
		} catch (WebServerException e) {
			throw new ServiceException("Error in adding blood pressure", e);
		}
	}

}
