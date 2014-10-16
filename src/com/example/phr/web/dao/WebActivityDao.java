package com.example.phr.web.dao;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.ActivitySingle;

public interface WebActivityDao {

	public int addReturnEntryId(ActivitySingle activity)
			throws WebServerException, OutdatedAccessTokenException;

	public List<ActivitySingle> getAll() throws WebServerException,
			OutdatedAccessTokenException;

	public List<ActivitySingle> search(String query) throws WebServerException,
			OutdatedAccessTokenException;
}
