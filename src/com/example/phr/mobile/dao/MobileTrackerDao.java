package com.example.phr.mobile.dao;

import java.util.ArrayList;

import java.text.ParseException;

import com.example.phr.exceptions.DataAccessException;

public interface MobileTrackerDao<T> {
	
	public void add(T object) throws DataAccessException;
	
	public ArrayList<T> getAll() throws ParseException;

}
