package com.example.phr.mobile.dao;

import java.text.ParseException;
import java.util.List;

import com.example.phr.mobile.models.MobileBloodPressure;

public interface MobileBloodPressureDao {

	void add(MobileBloodPressure bloodPressure);

	List<MobileBloodPressure> getAllBloodPressure() throws ParseException;
	
}
