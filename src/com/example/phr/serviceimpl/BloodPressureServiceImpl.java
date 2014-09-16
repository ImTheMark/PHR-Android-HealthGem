package com.example.phr.serviceimpl;

import java.sql.Timestamp;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileBloodPressureDao;
import com.example.phr.mobile.daoimpl.MobileBloodPressureDaoImpl;
import com.example.phr.mobile.models.BloodPressure;
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
	public void addBloodPressure(BloodPressure bloodPressure)
			throws ServiceException, OutdatedAccessTokenException {
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		System.out.println(timestamp);
		//try {
//			int entryID = webBloodPressureDao
//					.add_ReturnEntryIdInWeb(bloodPressure);
//			System.out.println(bloodPressure.getDateAdded());
//			bloodPressure.setEntryID(entryID);
			mobileBloodPressureDao.add(bloodPressure);
	//	} catch (WebServerException e) {
	//		throw new ServiceException(
	//				"An error occured while trying to add bp to web", e);
		//}

	}

}
