package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Food;

public interface FoodService {

	public int add(Food food) throws ServiceException;

	public List<Food> getAll();

	public List<Food> search(String query) throws ServiceException,
			OutdatedAccessTokenException;

}
