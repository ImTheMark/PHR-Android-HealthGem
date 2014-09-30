package com.example.phr.mobile.daoimpl;

import java.text.ParseException;
import java.util.ArrayList;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.mobile.dao.MobileFoodDao;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.FoodTrackerEntry;

public class MobileFoodDaoImpl implements MobileFoodDao {

	@Override
	public void add(FoodTrackerEntry object) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(FoodTrackerEntry object) throws DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<FoodTrackerEntry> getAll() throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addFoodListEntryReturnEntryID(Food food)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean checkFoodEntryInList(Food food) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Food> getAllFood() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
