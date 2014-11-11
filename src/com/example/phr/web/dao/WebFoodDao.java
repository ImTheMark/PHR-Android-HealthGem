package com.example.phr.web.dao;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.Food;

public interface WebFoodDao {

	public int addReturnEntryId(Food food) throws WebServerException,
			OutdatedAccessTokenException;

	public List<Food> getAll() throws WebServerException,
			OutdatedAccessTokenException;

	public List<Food> search(String query) throws WebServerException,
			OutdatedAccessTokenException;

	public void delete(Food food) throws WebServerException,
			OutdatedAccessTokenException;
}
