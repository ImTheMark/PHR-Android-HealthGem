package com.example.phr.mobile.dao;

import java.util.ArrayList;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.FoodTrackerEntry;

public interface MobileFoodDao extends MobileTrackerDao<FoodTrackerEntry> {

	public void addFoodListEntry(Food food)
			throws DataAccessException;

	public Boolean foodEntryExists(Food food) throws DataAccessException;

	public ArrayList<Food> getAllFood() throws DataAccessException;

}
