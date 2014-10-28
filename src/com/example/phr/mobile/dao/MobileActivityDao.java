package com.example.phr.mobile.dao;

import java.util.List;

import com.example.phr.mobile.models.Activity;

public interface MobileActivityDao {

	public void addReturnsEntryId(Activity activity);

	public List<Activity> getAll();

	public Activity get(int id);

}
