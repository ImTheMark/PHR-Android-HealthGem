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
			OutdatedAccessTokenException, WebServerException, DataAccessException {
		int entryID = webWeightDao.add_ReturnEntryIdInWeb(weight); 
		weight.setEntryID(entryID); 
		mobileWeightDao.add(weight);
	}

	@Override
	public void edit(Weight weight) throws WebServerException,
			OutdatedAccessTokenException, DataAccessException,
			EntryNotFoundException {
		webWeightDao.edit(weight);
		mobileWeightDao.edit(weight);
	}

	@Override
	public void delete(Weight weight) throws WebServerException,
			OutdatedAccessTokenException, DataAccessException, EntryNotFoundException {
		webWeightDao.delete(weight);
		mobileWeightDao.delete(weight);
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
