package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.ActivitySingle;
import com.example.phr.service.ActivityService;
import com.example.phr.web.dao.WebActivityDao;
import com.example.phr.web.daoimpl.WebActivityDaoImpl;

public class ActivityServiceImpl implements ActivityService {

	WebActivityDao webActivityDao = new WebActivityDaoImpl();

	@Override
	public int add(ActivitySingle activity)
			throws OutdatedAccessTokenException, ServiceException {
		try {
			return webActivityDao.addReturnEntryId(activity);
		} catch (WebServerException e) {
			throw new ServiceException("Error has occurred", e);
		}
	}

	@Override
	public List<ActivitySingle> getAll() throws ServiceException,
			OutdatedAccessTokenException {
		try {
			return webActivityDao.getAll();
		} catch (WebServerException e) {
			throw new ServiceException("Error has occurred", e);
		}

	}

	@Override
	public List<ActivitySingle> search(String query) throws ServiceException,
			OutdatedAccessTokenException {
		try {
			return webActivityDao.search(query);
		} catch (WebServerException e) {
			throw new ServiceException("Error has occurred", e);
		}
	}

}
