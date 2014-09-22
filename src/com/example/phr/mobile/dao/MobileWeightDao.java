package com.example.phr.mobile.dao;

import java.text.ParseException;
import java.util.List;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.Weight;

public interface MobileWeightDao {

	void add(Weight weight) throws DataAccessException;

	List<Weight> getAllWeight() throws ParseException;

}
