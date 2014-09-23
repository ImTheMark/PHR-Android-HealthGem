package com.example.phr.web.daoimpl;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.web.dao.WebBloodPressureDao;

public class WebBloodPressureDaoImpl extends
		GenericWebTrackerDaoImpl<BloodPressure> implements WebBloodPressureDao {

	@Override
	public int add_ReturnEntryIdInWeb(BloodPressure bloodPressure)
			throws WebServerException, OutdatedAccessTokenException {

		String command = "tracker/addBloodPressure";
		return add_ReturnEntryIDToWebUsingHttp(command, bloodPressure);

	}

	@Override
	public void edit(BloodPressure object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BloodPressure object) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BloodPressure> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllBloodPressure";
		return getAllUsingHttp(command, BloodPressure.class);
	}
}
