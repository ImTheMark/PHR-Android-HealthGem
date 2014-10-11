package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileBloodSugarTrackerDao;
import com.example.phr.mobile.daoimpl.MobileBloodSugarTrackerDaoImpl;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.service.BloodSugarTrackerService;
import com.example.phr.web.dao.WebBloodSugarTrackerDao;
import com.example.phr.web.daoimpl.WebBloodSugarTrackerDaoImpl;

public class BloodSugarTrackerServiceImpl implements BloodSugarTrackerService {

	WebBloodSugarTrackerDao webBloodSugarTrackerDao;
	MobileBloodSugarTrackerDao mobileBloodSugarTrackerDao;

	public BloodSugarTrackerServiceImpl() {
		webBloodSugarTrackerDao = new WebBloodSugarTrackerDaoImpl();
		mobileBloodSugarTrackerDao = new MobileBloodSugarTrackerDaoImpl();
	}

	@Override
	public void add(BloodSugar bloodSugar) throws ServiceException,
			OutdatedAccessTokenException {
		int entryID;
		try {
			entryID = webBloodSugarTrackerDao.add_ReturnEntryIdInWeb(bloodSugar);
			bloodSugar.setEntryID(entryID);
			mobileBloodSugarTrackerDao.add(bloodSugar);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add bs to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add bs to web", e);
		}
	}

	@Override
	public void edit(BloodSugar bloodSugar) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webBloodSugarTrackerDao.edit(bloodSugar);
			mobileBloodSugarTrackerDao.edit(bloodSugar);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to edit bs to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to edit bs to local", e);
		}
	}

	@Override
	public void delete(BloodSugar bloodSugar) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webBloodSugarTrackerDao.delete(bloodSugar);
			mobileBloodSugarTrackerDao.delete(bloodSugar);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to delete bs to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to delete bs to web", e);
		}
	}

	@Override
	public List<BloodSugar> getAll() throws ServiceException {
		try {
			return mobileBloodSugarTrackerDao.getAll();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to retrieve", e);
		}
	}

	@Override
	public BloodSugar get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
