package com.example.phr.mobile.dao;

import java.util.List;

import com.example.phr.mobile.models.Activity;

public interface MobileActivityDao {

	public void addReturnsEntryId(Activity activity);

	public Activity get(int id);

}
