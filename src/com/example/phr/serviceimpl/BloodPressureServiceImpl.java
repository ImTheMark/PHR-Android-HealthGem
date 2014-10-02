package com.example.phr.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
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
	public void add(BloodPressure bloodPressure) throws ServiceException,
			OutdatedAccessTokenException {
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		try {
			int entryID = webBloodPressureDao
					.add_ReturnEntryIdInWeb(bloodPressure);
			bloodPressure.setEntryID(entryID);
			mobileBloodPressureDao.add(bloodPressure);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add bp to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add bp to web", e);
		}

	}

	@Override
	public void edit(BloodPressure bloodPressure)
			throws OutdatedAccessTokenException, EntryNotFoundException,
			ServiceException {
		try {
			webBloodPressureDao.edit(bloodPressure);
			mobileBloodPressureDao.edit(bloodPressure);
		} catch (WebServerException e) {
			throw new ServiceException("Error performing action", e);
		} catch (DataAccessException e) {
			throw new ServiceException("Error performing action", e);
		}

	}

	@Override
	public void delete(BloodPressure bloodPressure) throws WebServerException,
			OutdatedAccessTokenException {
		webBloodPressureDao.delete(bloodPressure);
		// mobileBloodPressureDao.delete(bloodPressure);
	}

	@Override
	public List<BloodPressure> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BloodPressure get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
