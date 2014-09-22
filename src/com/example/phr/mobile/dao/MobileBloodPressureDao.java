package com.example.phr.mobile.dao;

import java.text.ParseException;
import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.BloodPressure;

public interface MobileBloodPressureDao {

	void add(BloodPressure bloodPressure) throws DataAccessException;

	List<BloodPressure> getAllBloodPressure() throws ParseException;

}
