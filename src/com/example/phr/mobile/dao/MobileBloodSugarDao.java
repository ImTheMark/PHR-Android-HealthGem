package com.example.phr.mobile.dao;

import java.text.ParseException;
import java.util.List;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.BloodSugar;

public interface MobileBloodSugarDao {

	void add(BloodSugar bloodSugar) throws DataAccessException;

	List<BloodSugar> getAllBloodSugar() throws ParseException;

}
