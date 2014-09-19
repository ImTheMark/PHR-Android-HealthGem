package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;

public interface TrackerService<T> {

	public void add(T object) throws ServiceException,
			OutdatedAccessTokenException;

	public void edit(T object);

	public void delete(T object);

	public List<T> getAll();

	public T get(int entryID);
}
