package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileWeightTrackerDao;
import com.example.phr.mobile.daoimpl.MobileWeightTrackerDaoImpl;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.web.dao.WebWeightTrackerDao;
import com.example.phr.web.daoimpl.WebWeightTrackerDaoImpl;

public class WeightTrackerServiceImpl implements WeightTrackerService {

	WebWeightTrackerDao webWeightTrackerDao;
	MobileWeightTrackerDao mobileWeightTrackerDao;

	public WeightTrackerServiceImpl() {
		webWeightTrackerDao = new WebWeightTrackerDaoImpl();
		mobileWeightTrackerDao = new MobileWeightTrackerDaoImpl();
	}

	@Override
	public void add(Weight weight) throws ServiceException,
			OutdatedAccessTokenException {
		int entryID;
		try {
			entryID = webWeightTrackerDao.add_ReturnEntryIdInWeb(weight);
			weight.setEntryID(entryID);
			mobileWeightTrackerDao.add(weight);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add weight to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add weight to web", e);
		}
	}

	@Override
	public void edit(Weight weight) throws OutdatedAccessTokenException,
			EntryNotFoundException, ServiceException {
		try {
			webWeightTrackerDao.edit(weight);
			mobileWeightTrackerDao.edit(weight);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to edit weight to web", e);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to edit weight to web", e);
		}
	}

	@Override
	public void delete(Weight weight) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webWeightTrackerDao.delete(weight);
			mobileWeightTrackerDao.delete(weight);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to delete weight to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to delete weight to web", e);
		}
	}

	@Override
	public List<Weight> getAll() throws ServiceException {
		try {
			return mobileWeightTrackerDao.getAll();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to retrieve", e);
		}
	}

	@Override
	public Weight get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Weight getLatest()throws ServiceException{
		try {
			return mobileWeightTrackerDao.getLatest();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to get latest weight from local db", e);
		}
	}

}
