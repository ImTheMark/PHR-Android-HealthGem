package com.example.phr.service;

import java.util.List;

import com.example.phr.mobile.models.Food;

public interface FoodService {

	public void add(Food food);
	
	public List<Food> getAll();
	
	public List<Food> search(String query);
	
}
