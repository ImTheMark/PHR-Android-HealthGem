package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileActivityTrackerDao;
import com.example.phr.mobile.daoimpl.MobileActivityTrackerDaoImpl;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.service.ActivityService;
import com.example.phr.service.ActivityTrackerService;
import com.example.phr.web.dao.WebActivityTrackerDao;
import com.example.phr.web.daoimpl.WebActivityTrackerDaoImpl;

public class ActivityTrackerServiceImpl implements ActivityTrackerService {

	WebActivityTrackerDao webActivityTrackerDao = new WebActivityTrackerDaoImpl();
	MobileActivityTrackerDao mobileActivityTrackerDao = new MobileActivityTrackerDaoImpl();
	ActivityService activityService = new ActivityServiceImpl();

	@Override
	public void add(ActivityTrackerEntry activity) throws ServiceException,
			OutdatedAccessTokenException {

		try {
			int activityEntryId = activityService.add(activity.getActivity());
			activity.getActivity().setEntryID(activityEntryId);
			int foodTrackerEntryId = webActivityTrackerDao
					.add_ReturnEntryIdInWeb(activity);
			activity.setEntryID(foodTrackerEntryId);
			mobileActivityTrackerDao.add(activity);
		} catch (DataAccessException e) {
			throw new ServiceException("Error", e);
		} catch (WebServerException e) {
			throw new ServiceException("Error", e);
		}

	}

	@Override
	public void edit(ActivityTrackerEntry activity) throws ServiceException, OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webActivityTrackerDao.edit(activity);
			mobileActivityTrackerDao.edit(activity);
		} catch (WebServerException e) {
			throw new ServiceException("Error", e);
		} catch (DataAccessException e) {
			throw new ServiceException("Error", e);
		}
		
	}

	@Override
	public void delete(ActivityTrackerEntry activity) throws OutdatedAccessTokenException, EntryNotFoundException, ServiceException {
		try {
			webActivityTrackerDao.delete(activity);
			mobileActivityTrackerDao.delete(activity);
		} catch (WebServerException e) {
			throw new ServiceException("Error", e);
		} catch (DataAccessException e) {
			throw new ServiceException("Error", e);
		}
	}

	@Override
	public List<ActivityTrackerEntry> getAll() throws ServiceException {
		try {
			return mobileActivityTrackerDao.getAllReversed();
		} catch (DataAccessException e) {
			throw new ServiceException("Error", e);
		}
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
