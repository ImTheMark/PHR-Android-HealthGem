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
			OutdatedAccessTokenException, WebServerException, DataAccessException {
		int entryID = webCheckUpDao.add_ReturnEntryIdInWeb(checkUp); 
		checkUp.setEntryID(entryID); 
		mobileCheckUpDao.add(checkUp);
	}

	@Override
	public void edit(CheckUp checkUp) throws WebServerException,
			OutdatedAccessTokenException, DataAccessException,
			EntryNotFoundException {
		webCheckUpDao.edit(checkUp);
		mobileCheckUpDao.edit(checkUp);
	}

	@Override
	public void delete(CheckUp checkUp) throws WebServerException,
			OutdatedAccessTokenException, DataAccessException, EntryNotFoundException {
		webCheckUpDao.delete(checkUp);
		mobileCheckUpDao.delete(checkUp);
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
