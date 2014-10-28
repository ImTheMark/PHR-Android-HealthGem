package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Activity;

public interface ActivityService {

	public int add(Activity activity)
			throws OutdatedAccessTokenException, ServiceException;

	public List<Activity> getAll() throws ServiceException,
			OutdatedAccessTokenException;

	public List<Activity> search(String query) throws ServiceException,
			OutdatedAccessTokenException;

}
