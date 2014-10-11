package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.service.FoodTrackerService;

public class FoodTrackerServiceImpl implements FoodTrackerService {

	@Override
	public void add(FoodTrackerEntry object) throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(FoodTrackerEntry object) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(FoodTrackerEntry object) throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FoodTrackerEntry> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodTrackerEntry get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
