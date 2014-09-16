package com.example.phr.web.dao;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;

public interface WebTrackerDao<T> {

	public int add_ReturnEntryIdInWeb(T object) throws WebServerException,
			OutdatedAccessTokenException;

	public void edit(T object);

	public void delete(T object);

	public T get(int entryID);

	public List<T> getAll();
}
