package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;

public interface TrackerService<T> {

	public void add(T object) throws ServiceException,
			OutdatedAccessTokenException;

	public void edit(T object) throws WebServerException, OutdatedAccessTokenException, DataAccessException, EntryNotFoundException;

	public void delete(T object) throws WebServerException, OutdatedAccessTokenException;

	public List<T> getAll();

	public T get(int entryID);
}
