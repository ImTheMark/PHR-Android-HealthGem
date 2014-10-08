package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileBloodSugarDao;
import com.example.phr.mobile.daoimpl.MobileBloodSugarDaoImpl;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.service.BloodSugarService;
import com.example.phr.web.dao.WebBloodSugarDao;
import com.example.phr.web.daoimpl.WebBloodSugarDaoImpl;

public class BloodSugarServiceImpl implements BloodSugarService {

	WebBloodSugarDao webBloodSugarDao;
	MobileBloodSugarDao mobileBloodSugarDao;

	public BloodSugarServiceImpl() {
		webBloodSugarDao = new WebBloodSugarDaoImpl();
		mobileBloodSugarDao = new MobileBloodSugarDaoImpl();
	}

	@Override
	public void add(BloodSugar bloodSugar) throws ServiceException,
			OutdatedAccessTokenException {
		int entryID;
		try {
			entryID = webBloodSugarDao.add_ReturnEntryIdInWeb(bloodSugar);
			bloodSugar.setEntryID(entryID);
			mobileBloodSugarDao.add(bloodSugar);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add bs to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add bs to web", e);
		}
	}

	@Override
	public void edit(BloodSugar bloodSugar) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webBloodSugarDao.edit(bloodSugar);
			mobileBloodSugarDao.edit(bloodSugar);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to edit bs to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to edit bs to local", e);
		}
	}

	@Override
	public void delete(BloodSugar bloodSugar) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webBloodSugarDao.delete(bloodSugar);
			mobileBloodSugarDao.delete(bloodSugar);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to delete bs to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to delete bs to web", e);
		}
	}

	@Override
	public List<BloodSugar> getAll() throws ServiceException {
		try {
			return mobileBloodSugarDao.getAll();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to retrieve", e);
		}
	}

	@Override
	public BloodSugar get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
