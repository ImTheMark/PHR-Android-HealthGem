package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;

public interface TrackerService<T> {

	public void add(T object) throws ServiceException,
			OutdatedAccessTokenException, ServiceException;

	public void edit(T object) throws OutdatedAccessTokenException,
			EntryNotFoundException, ServiceException;

	public void delete(T object) throws OutdatedAccessTokenException, 
			EntryNotFoundException, ServiceException;

	public List<T> getAll();

	public T get(int entryID);
}
