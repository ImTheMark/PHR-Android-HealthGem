package com.example.phr.serviceimpl;

import java.util.List;

import android.util.Log;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileCheckupTrackerDao;
import com.example.phr.mobile.daoimpl.MobileCheckupTrackerDaoImpl;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.service.CheckUpTrackerService;
import com.example.phr.web.dao.WebCheckUpTrackerDao;
import com.example.phr.web.daoimpl.WebCheckUpTrackerDaoImpl;

public class CheckUpTrackerServiceImpl implements CheckUpTrackerService {

	WebCheckUpTrackerDao webCheckUpTrackerDao;
	MobileCheckupTrackerDao mobileCheckUpTrackerDao;

	public CheckUpTrackerServiceImpl() {
		webCheckUpTrackerDao = new WebCheckUpTrackerDaoImpl();
		mobileCheckUpTrackerDao = new MobileCheckupTrackerDaoImpl();
	}

	@Override
	public void add(CheckUp checkUp) throws ServiceException,
			OutdatedAccessTokenException {
		int entryID;
		try {
			entryID = webCheckUpTrackerDao.add_ReturnEntryIdInWeb(checkUp);
			checkUp.setEntryID(entryID);
			mobileCheckUpTrackerDao.add(checkUp);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add cu to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add cu to web", e);
		}
	}

	@Override
	public void edit(CheckUp checkUp) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			Log.e("checkupserviceimpl", "edit");
			webCheckUpTrackerDao.edit(checkUp);
			mobileCheckUpTrackerDao.edit(checkUp);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to edit cu to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to edit cu to web", e);
		}
	}

	@Override
	public void delete(CheckUp checkUp) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			Log.e("checkupserviceimpl", "del");
			webCheckUpTrackerDao.delete(checkUp);
			mobileCheckUpTrackerDao.delete(checkUp);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to delete cu to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to delete cu to web", e);
		}
	}

	@Override
	public List<CheckUp> getAll() throws ServiceException {
		try {
			return mobileCheckUpTrackerDao.getAll();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to retrieve", e);
		}
	}

	@Override
	public CheckUp get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckUp getLatest() throws ServiceException {
		try {
			return mobileCheckUpTrackerDao.getLatest();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to get latest checkup from local db",
					e);
		}
	}

	@Override
	public void initializeMobileDatabase() throws ServiceException {
		try {
			List<CheckUp> list = webCheckUpTrackerDao.getAll();
			for (CheckUp entry : list) {
				mobileCheckUpTrackerDao.add(entry);
			}
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to get latest checkup from local db",
					e);
		} catch (OutdatedAccessTokenException e) {
			throw new ServiceException(
					"An error occured while trying to get latest checkup from local db",
					e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to get latest checkup from local db",
					e);
		}
	}

}
