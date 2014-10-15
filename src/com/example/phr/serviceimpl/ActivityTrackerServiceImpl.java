package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.dao.MobileActivityTrackerDao;
import com.example.phr.mobile.daoimpl.MobileActivityTrackerDaoImpl;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.service.ActivityTrackerService;

public class ActivityTrackerServiceImpl implements ActivityTrackerService {

	// webActivityTrackerDao webActivityTrackerDao;
	MobileActivityTrackerDao mobileActivityTrackerDao;

	public ActivityTrackerServiceImpl() {
		// webActivityTrackerDao = new webActivityTrackerDaoImpl();
		mobileActivityTrackerDao = new MobileActivityTrackerDaoImpl();
	}

	@Override
	public void add(ActivityTrackerEntry activity) throws ServiceException,
			OutdatedAccessTokenException {
		/*
		 * try { int entryID = webActivityTrackerDao
		 * .add_ReturnEntryIdInWeb(activity);
		 * webActivityTrackerDao.setEntryID(entryID);
		 * mobileActivityTrackerDao.add(activity); } catch (WebServerException
		 * e) { throw new ServiceException(
		 * "An error occured while trying to add activity to web", e); } catch
		 * (DataAccessException e) { throw new ServiceException(
		 * "An error occured while trying to add activity to web", e); }
		 */

	}

	@Override
	public void edit(ActivityTrackerEntry activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(ActivityTrackerEntry activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ActivityTrackerEntry> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityTrackerEntry get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityTrackerEntry getLatest() throws ServiceException{
		try {
			return mobileActivityTrackerDao.getLatest();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to get latest actTracker from local db", e);
		}
	}
}
