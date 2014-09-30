package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.Weight;
import com.example.phr.web.dao.WebWeightDao;
import com.google.gson.reflect.TypeToken;

public class WebWeightDaoImpl extends GenericWebTrackerDaoImpl<Weight>
		implements WebWeightDao {

	@Override
	public int add_ReturnEntryIdInWeb(Weight object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/addWeight";
		return add_ReturnEntryIDToWebUsingHttp(command, object);
	}

	@Override
	public void edit(Weight object) throws WebServerException, OutdatedAccessTokenException {
		String command = "tracker/editWeight";
		editUsingHttp(command, object);
	}

	@Override
	public void delete(Weight object) throws WebServerException, OutdatedAccessTokenException {
		String command = "tracker/deleteWeight";
		deleteUsingHttp(command, object);
	}

	@Override
	public List<Weight> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllWeight";
		Type type = new TypeToken<List<Weight>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

}
