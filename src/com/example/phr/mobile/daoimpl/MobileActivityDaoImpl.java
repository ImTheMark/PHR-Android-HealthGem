package com.example.phr.mobile.daoimpl;

import java.text.ParseException;
import java.util.ArrayList;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.mobile.dao.MobileActivityDao;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.ActivityTrackerEntry;

public class MobileActivityDaoImpl implements MobileActivityDao {

	@Override
	public void add(ActivityTrackerEntry object) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(ActivityTrackerEntry object) throws DataAccessException, EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ActivityTrackerEntry> getAll() throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addActivityListEntryReturnEntryID(Activity activity) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Activity> getAllActivity() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ActivityTrackerEntry object) throws DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}
}
