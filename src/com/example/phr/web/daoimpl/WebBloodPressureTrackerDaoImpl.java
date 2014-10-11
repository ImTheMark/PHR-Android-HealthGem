package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.web.dao.WebBloodPressureTrackerDao;
import com.google.gson.reflect.TypeToken;

public class WebBloodPressureTrackerDaoImpl extends
		GenericWebTrackerDaoImpl<BloodPressure> implements WebBloodPressureTrackerDao {

	@Override
	public int add_ReturnEntryIdInWeb(BloodPressure object)
			throws WebServerException, OutdatedAccessTokenException {

		String command = "tracker/addBloodPressure";
		return add_ReturnEntryIDToWebUsingHttp(command, object);

	}

	@Override
	public void edit(BloodPressure object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/editBloodPressure";
		editUsingHttp(command, object);
	}

	@Override
	public void delete(BloodPressure object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/deleteBloodPressure";
		deleteUsingHttp(command, object);
	}

	@Override
	public List<BloodPressure> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllBloodPressure";
		Type type = new TypeToken<List<BloodPressure>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}
}
