package com.example.phr.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.*;
import com.example.phr.mobile.daoimpl.MobileActivityDaoImpl;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
	
	//WebActivityDao webActivityDao;
	MobileActivityDao mobileActivityDao;

	public ActivityServiceImpl() {
		//webActivityDao = new WebActivityDaoImpl();
		mobileActivityDao = new MobileActivityDaoImpl();
	}


	@Override
	public void add(ActivityTrackerEntry activity) throws ServiceException,
			OutdatedAccessTokenException {
		/*try {
			int entryID = webActivityDao
					.add_ReturnEntryIdInWeb(activity);
			webActivityDao.setEntryID(entryID);
			mobileActivityDao.add(activity);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add activity to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add activity to web", e);
		}*/

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

}
