package com.example.phr.web.dao;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;

public interface WebTrackerDao<TrackerEntry> {

	public int add_ReturnEntryIdInWeb(TrackerEntry object)
			throws WebServerException, OutdatedAccessTokenException;

	public void edit(TrackerEntry object);

	public void delete(TrackerEntry object);

	public List<TrackerEntry> getAll() throws WebServerException,
			OutdatedAccessTokenException;
}
