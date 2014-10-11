package com.example.phr.service;

import java.util.List;

import com.example.phr.model.Activity;

public interface ActivityService {

	public void add(Activity activity);
	
	public List<Activity> getAll();
	
	public List<Activity> search(String query);
	
}
