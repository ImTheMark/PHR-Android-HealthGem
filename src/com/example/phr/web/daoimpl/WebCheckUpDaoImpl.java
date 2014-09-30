package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.web.dao.WebCheckUpDao;
import com.google.gson.reflect.TypeToken;

public class WebCheckUpDaoImpl extends
	GenericWebTrackerDaoImpl<CheckUp>implements WebCheckUpDao {

	@Override
	public int add_ReturnEntryIdInWeb(CheckUp object)
			throws WebServerException, OutdatedAccessTokenException {
		String command = "tracker/addCheckUp";
		return add_ReturnEntryIDToWebUsingHttp(command, object);
	}

	@Override
	public void edit(CheckUp object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/editCheckUp";
		editUsingHttp(command, object);
	}

	@Override
	public void delete(CheckUp object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/deleteCheckUp";
		deleteUsingHttp(command, object);
	}

	@Override
	public List<CheckUp> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllCheckUp";
		Type type = new TypeToken<List<CheckUp>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

}
