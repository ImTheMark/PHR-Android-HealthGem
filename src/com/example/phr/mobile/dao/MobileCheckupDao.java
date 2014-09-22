package com.example.phr.mobile.dao;

import java.text.ParseException;
import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.CheckUp;

public interface MobileCheckupDao {

	void add(CheckUp checkUp) throws DataAccessException;

	List<CheckUp> getAllCheckUp() throws ParseException;

}
