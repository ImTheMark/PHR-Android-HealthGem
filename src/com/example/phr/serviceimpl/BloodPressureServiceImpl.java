package com.example.phr.serviceimpl;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.dao.MobileBloodPressureDao;
import com.example.phr.mobile.daoimpl.MobileBloodPressureDaoImpl;
import com.example.phr.mobile.models.MobileBloodPressure;
import com.example.phr.service.BloodPressureService;
import com.example.phr.web.dao.WebBloodPressureDao;
import com.example.phr.web.daoimpl.WebBloodPressureDaoImpl;

public class BloodPressureServiceImpl implements BloodPressureService {

	WebBloodPressureDao webBloodPressureDao;
	MobileBloodPressureDao mobileBloodPressureDao;

	public BloodPressureServiceImpl() {
		webBloodPressureDao = new WebBloodPressureDaoImpl();
		mobileBloodPressureDao = new MobileBloodPressureDaoImpl();
	}

	@Override
	public void addBloodPressure(MobileBloodPressure bloodPressure)
			throws ServiceException, OutdatedAccessTokenException {

		mobileBloodPressureDao.add(bloodPressure);

	}

}
