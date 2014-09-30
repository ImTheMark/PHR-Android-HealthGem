package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.WeightService;

public class WeightServiceImpl implements WeightService {

	@Override
	public void add(Weight object) throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(Weight object) throws WebServerException,
			OutdatedAccessTokenException, DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Weight object) throws WebServerException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Weight> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Weight get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
