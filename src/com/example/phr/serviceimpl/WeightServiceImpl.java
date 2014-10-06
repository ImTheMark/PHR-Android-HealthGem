package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileWeightDao;
import com.example.phr.mobile.daoimpl.MobileWeightDaoImpl;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.WeightService;
import com.example.phr.web.dao.WebWeightDao;
import com.example.phr.web.daoimpl.WebWeightDaoImpl;

public class WeightServiceImpl implements WeightService {

	WebWeightDao webWeightDao;
	MobileWeightDao mobileWeightDao;

	public WeightServiceImpl() {
		webWeightDao = new WebWeightDaoImpl();
		mobileWeightDao = new MobileWeightDaoImpl();
	}

	@Override
	public void add(Weight weight) throws ServiceException,
			OutdatedAccessTokenException {
		int entryID;
		try {
			entryID = webWeightDao.add_ReturnEntryIdInWeb(weight);
			weight.setEntryID(entryID); 
			mobileWeightDao.add(weight);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add weight to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add weight to web", e);
		} 
	}

	@Override
	public void edit(Weight weight) throws OutdatedAccessTokenException, EntryNotFoundException, ServiceException {
		try {
			webWeightDao.edit(weight);
			mobileWeightDao.edit(weight);
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
			webWeightDao.delete(weight);
			mobileWeightDao.delete(weight);
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
			return mobileWeightDao.getAll();
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

}
