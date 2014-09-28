package com.example.phr.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import com.example.phr.exceptions.DataAccessException;
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
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		/*try {
			int entryID = webBloodSugarDao
					.add_ReturnEntryIdInWeb(bloodSugar); 
			bloodSugar.setEntryID(entryID); 
			mobileBloodSugarDao.add(bloodSugar);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add bp to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add bp to web", e);
		}*/

	}

	@Override
	public void edit(BloodSugar object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BloodSugar object) {
		// TODO Auto-generated method stub

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
