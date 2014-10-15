package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;

public interface TrackerService<T> {

	public void add(T object) throws ServiceException,
			OutdatedAccessTokenException, ServiceException;

	public void edit(T object) throws OutdatedAccessTokenException,
			EntryNotFoundException, ServiceException;

	public void delete(T object) throws OutdatedAccessTokenException,
			EntryNotFoundException, ServiceException;

	public List<T> getAll() throws ServiceException;

	public T get(int entryID) throws ServiceException;

	public T getLatest() throws ServiceException;
}
