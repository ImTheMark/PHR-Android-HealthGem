package com.example.phr.web.daoimpl;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.web.dao.WebBloodPressureDao;

public class WebBloodPressureDaoImpl extends BasicDaoImpl<BloodPressure>
		implements WebBloodPressureDao {
	@Override
	public int add_ReturnEntryIdInWeb(BloodPressure bloodPressure)
			throws WebServerException, OutdatedAccessTokenException {

		String command = "tracker/addBloodPressure";
		String fieldName = "bloodPressure";
		return add_ReturnIDToWebUsingHttp(command, fieldName, bloodPressure);

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
	public BloodPressure get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BloodPressure> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
