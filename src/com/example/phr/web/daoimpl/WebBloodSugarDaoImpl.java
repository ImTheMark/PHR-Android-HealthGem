package com.example.phr.web.daoimpl;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.model.BloodSugar;
import com.example.phr.web.dao.WebBloodSugarDao;

public class WebBloodSugarDaoImpl extends GenericWebTrackerDaoImpl<BloodSugar>
		implements WebBloodSugarDao {

	@Override
	public int add_ReturnEntryIdInWeb(BloodSugar bloodSugar)
			throws WebServerException, OutdatedAccessTokenException {
		String command = "tracker/addBloodSugar";
		String fieldName = "bloodSugar";
		return add_ReturnEntryIDToWebUsingHttp(command, bloodSugar);
	}

	@Override
	public void edit(BloodSugar bloodSugar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BloodSugar bloodSugar) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BloodSugar> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllBloodSugar";
		return getAllUsingHttp(command, BloodSugar.class);
	}

}
