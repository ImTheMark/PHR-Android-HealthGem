package com.example.phr.mobile.dao;

import java.util.ArrayList;
import java.text.ParseException;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;

public interface MobileTrackerDao<T> {
	
	public void add(T object) throws DataAccessException;

	public void edit(T object) throws DataAccessException,
			EntryNotFoundException;
	
	public ArrayList<T> getAll() throws ParseException;

	public void delete(T object) throws DataAccessException,
			EntryNotFoundException;

}
