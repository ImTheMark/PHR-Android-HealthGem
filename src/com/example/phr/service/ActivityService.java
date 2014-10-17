package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.ActivitySingle;

public interface ActivityService {

	public int add(ActivitySingle activity)
			throws OutdatedAccessTokenException, ServiceException;

	public List<ActivitySingle> getAll() throws ServiceException,
			OutdatedAccessTokenException;

	public List<ActivitySingle> search(String query) throws ServiceException,
			OutdatedAccessTokenException;

}
