package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.web.dao.WebFoodTrackerEntryDao;
import com.google.gson.reflect.TypeToken;

public class WebFoodTrackerEntryDaoImpl extends
		GenericWebTrackerDaoImpl<FoodTrackerEntry> implements WebFoodTrackerEntryDao {

	@Override
	public int add_ReturnEntryIdInWeb(FoodTrackerEntry object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/addFoodTrackerEntry";
		return add_ReturnEntryIDToWebUsingHttp(command, object);
	}

	@Override
	public void edit(FoodTrackerEntry object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/editFoodTrackerEntry";
		editUsingHttp(command, object);
	}

	@Override
	public void delete(FoodTrackerEntry object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/deleteFoodTrackerEntry";
		deleteUsingHttp(command, object);
	}

	@Override
	public List<FoodTrackerEntry> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllFoodTrackerEntry";
		Type type = new TypeToken<List<FoodTrackerEntry>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

}
