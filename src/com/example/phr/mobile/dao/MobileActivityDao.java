package com.example.phr.mobile.dao;

import java.util.List;

import com.example.phr.mobile.models.ActivitySingle;

public interface MobileActivityDao {

	public int addReturnsEntryId(ActivitySingle activity);

	public List<ActivitySingle> getAll();

	public ActivitySingle get(int id);

}
