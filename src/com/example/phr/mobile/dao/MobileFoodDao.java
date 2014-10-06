package com.example.phr.mobile.dao;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.FoodTrackerEntry;

public interface MobileFoodDao extends MobileTrackerDao<FoodTrackerEntry> {

	public void addFoodListEntry(SQLiteDatabase db, Food food)
			throws DataAccessException;

	public Boolean foodListEntryExists(SQLiteDatabase db, Food food) throws DataAccessException;

	public ArrayList<Food> getAllFoodListEntry() throws DataAccessException;

	public Food getFoodListEntry(SQLiteDatabase db, Integer foodListEntryID) throws DataAccessException;

}
