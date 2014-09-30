package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileCheckupDao;
import com.example.phr.mobile.daoimpl.MobileCheckupDaoImpl;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.service.CheckUpService;

public class CheckUpServiceImpl implements CheckUpService {

	//WebCheckUpDao webCheckUpDao;
	MobileCheckupDao mobileCheckUpDao;

	public CheckUpServiceImpl() {
		//webCheckUpDao = new WebCheckUpDaoImpl();
		mobileCheckUpDao = new MobileCheckupDaoImpl();
	}

	@Override
	public void add(CheckUp object) throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(CheckUp object) throws WebServerException,
			OutdatedAccessTokenException, DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(CheckUp object) throws WebServerException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CheckUp> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckUp get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
