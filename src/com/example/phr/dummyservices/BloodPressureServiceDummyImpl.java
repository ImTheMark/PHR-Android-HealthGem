package com.example.phr.dummyservices;

import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.service.BloodPressureTrackerService;

public class BloodPressureServiceDummyImpl implements BloodPressureTrackerService{

	@Override
	public void add(BloodPressure object) throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(BloodPressure object) throws OutdatedAccessTokenException,
			EntryNotFoundException, ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(BloodPressure object)
			throws OutdatedAccessTokenException, EntryNotFoundException,
			ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BloodPressure> getAll() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BloodPressure get(int entryID) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BloodPressure getLatest() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
