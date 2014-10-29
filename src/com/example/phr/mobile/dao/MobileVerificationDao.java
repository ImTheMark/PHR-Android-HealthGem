package com.example.phr.mobile.dao;

import java.util.List;

import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.Food;

public interface MobileVerificationDao {
	
	public void addFoodList(List<Food> foodList);
	
	public void addActivityList(List<Activity> activityList);
	
	public List<Food> getFoodList();
	
	public List<Activity> getActivityList();

}
