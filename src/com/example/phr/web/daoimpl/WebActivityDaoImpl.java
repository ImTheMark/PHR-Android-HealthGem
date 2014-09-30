package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.web.dao.WebActivityDao;
import com.google.gson.reflect.TypeToken;

public class WebActivityDaoImpl extends
		GenericWebTrackerDaoImpl<Activity> implements WebActivityDao {

	@Override
	public int add_ReturnEntryIdInWeb(Activity object)
			throws WebServerException, OutdatedAccessTokenException {
		String command = "tracker/addActivity";
		return add_ReturnEntryIDToWebUsingHttp(command, object);
	}

	@Override
	public void edit(Activity object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/editActivity";
		editUsingHttp(command, object);
	}

	@Override
	public void delete(Activity object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/deleteActivity";
		deleteUsingHttp(command, object);
	}

	@Override
	public List<Activity> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllActivity";
		Type type = new TypeToken<List<Activity>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

}
