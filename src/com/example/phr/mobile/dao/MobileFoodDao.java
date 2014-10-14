package com.example.phr.mobile.dao;

import java.util.List;

import com.example.phr.mobile.models.Food;

public interface MobileFoodDao {

	public int addReturnsEntryId(Food food);

	public List<Food> getAll();

	public Food get(int id);

}
