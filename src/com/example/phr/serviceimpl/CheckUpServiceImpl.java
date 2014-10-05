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
import com.example.phr.web.dao.WebCheckUpDao;
import com.example.phr.web.daoimpl.WebCheckUpDaoImpl;

public class CheckUpServiceImpl implements CheckUpService {

	WebCheckUpDao webCheckUpDao;
	MobileCheckupDao mobileCheckUpDao;

	public CheckUpServiceImpl() {
		webCheckUpDao = new WebCheckUpDaoImpl();
		mobileCheckUpDao = new MobileCheckupDaoImpl();
	}

	@Override
	public void add(CheckUp checkUp) throws ServiceException,
			OutdatedAccessTokenException {
		int entryID;
		try {
			entryID = webCheckUpDao.add_ReturnEntryIdInWeb(checkUp);
			checkUp.setEntryID(entryID); 
			mobileCheckUpDao.add(checkUp);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add cu to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add cu to web", e);
		} 
	}

	@Override
	public void edit(CheckUp checkUp) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webCheckUpDao.edit(checkUp);
			mobileCheckUpDao.edit(checkUp);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to edit cu to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to edit cu to web", e);
		}
	}

	@Override
	public void delete(CheckUp checkUp) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webCheckUpDao.delete(checkUp);
			mobileCheckUpDao.delete(checkUp);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to delete cu to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to delete cu to web", e);
		}
	}

	@Override
	public List<CheckUp> getAll() throws ServiceException {
		try {
			return mobileCheckUpDao.getAll();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to retrieve", e);
		}
	}

	@Override
	public CheckUp get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
