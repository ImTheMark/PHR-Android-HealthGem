package com.example.phr.serviceimpl;

import java.sql.Timestamp;
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
			OutdatedAccessTokenException, WebServerException, DataAccessException {
		int entryID = webBloodSugarDao.add_ReturnEntryIdInWeb(bloodSugar); 
		bloodSugar.setEntryID(entryID); 
		mobileBloodSugarDao.add(bloodSugar);
	}

	@Override
	public void edit(BloodSugar bloodSugar) throws WebServerException, OutdatedAccessTokenException, DataAccessException, EntryNotFoundException {
		webBloodSugarDao.edit(bloodSugar);
		mobileBloodSugarDao.edit(bloodSugar);
	}

	@Override
	public void delete(BloodSugar bloodSugar) throws WebServerException, OutdatedAccessTokenException, DataAccessException, EntryNotFoundException {		
		webBloodSugarDao.delete(bloodSugar);
		mobileBloodSugarDao.delete(bloodSugar);
	}

	@Override
	public List<BloodSugar> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BloodSugar get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
