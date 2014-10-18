package com.example.phr.mobile.dao;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.Food;

public interface MobileFoodDao {

	public void add(Food food) throws DataAccessException;;

	public List<Food> getAll() throws DataAccessException;;

	public Food get(int id) throws DataAccessException;;

}
