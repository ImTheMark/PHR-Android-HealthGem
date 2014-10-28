package com.example.phr.web.dao;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.Activity;

public interface WebActivityDao {

	public int addReturnEntryId(Activity activity)
			throws WebServerException, OutdatedAccessTokenException;

	public List<Activity> getAll() throws WebServerException,
			OutdatedAccessTokenException;

	public List<Activity> search(String query) throws WebServerException,
			OutdatedAccessTokenException;
}
