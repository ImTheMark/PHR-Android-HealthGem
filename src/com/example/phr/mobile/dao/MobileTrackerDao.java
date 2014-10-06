package com.example.phr.mobile.dao;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;

public interface MobileTrackerDao<T> {
	
	public void add(T object) throws DataAccessException;

	public void edit(T object) throws DataAccessException,
			EntryNotFoundException;
	
	public ArrayList<T> getAll() throws DataAccessException;

	public void delete(T object) throws DataAccessException,
			EntryNotFoundException;
	
	public List<T> getAllReversed() throws  DataAccessException;

}
