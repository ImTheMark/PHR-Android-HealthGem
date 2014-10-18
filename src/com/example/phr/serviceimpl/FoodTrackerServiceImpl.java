package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileFoodTrackerDao;
import com.example.phr.mobile.daoimpl.MobileFoodTrackerDaoImpl;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.service.FoodTrackerService;
import com.example.phr.web.dao.WebFoodTrackerDao;
import com.example.phr.web.daoimpl.WebFoodTrackerDaoImpl;

public class FoodTrackerServiceImpl implements FoodTrackerService {

	WebFoodTrackerDao webFoodTrackerDao;
	MobileFoodTrackerDao mobileFoodTrackerDao;
	
	public FoodTrackerServiceImpl(){
		webFoodTrackerDao = new WebFoodTrackerDaoImpl();
		mobileFoodTrackerDao = new MobileFoodTrackerDaoImpl();
	}

	@Override
	public void add(FoodTrackerEntry foodTrackerEntry) throws ServiceException,
			OutdatedAccessTokenException {
		try {
			webFoodTrackerDao.add_ReturnEntryIdInWeb(foodTrackerEntry);
			mobileFoodTrackerDao.add(foodTrackerEntry);
		} catch (WebServerException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void edit(FoodTrackerEntry foodTrackerEntry) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webFoodTrackerDao.edit(foodTrackerEntry);
			mobileFoodTrackerDao.edit(foodTrackerEntry);
		} catch (WebServerException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(FoodTrackerEntry foodTrackerEntry) throws ServiceException,
			OutdatedAccessTokenException {
		try {
			webFoodTrackerDao.delete(foodTrackerEntry);
			mobileFoodTrackerDao.delete(foodTrackerEntry);
		} catch (WebServerException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (EntryNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<FoodTrackerEntry> getAll() throws ServiceException {
		try {
			return mobileFoodTrackerDao.getAll();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add cu to web", e);
		}
	}

	@Override
	public FoodTrackerEntry get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodTrackerEntry getLatest() throws ServiceException{
		try {
			return mobileFoodTrackerDao.getLatest();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to get latest foodTracker from local db", e);
		}
	}
}
