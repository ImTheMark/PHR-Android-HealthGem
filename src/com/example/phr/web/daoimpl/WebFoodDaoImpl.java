package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.Food;
import com.example.phr.web.dao.WebFoodDao;
import com.google.gson.reflect.TypeToken;

public class WebFoodDaoImpl extends
		GenericWebTrackerDaoImpl<Food> implements WebFoodDao {

	@Override
	public int add_ReturnEntryIdInWeb(Food object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/addFood";
		return add_ReturnEntryIDToWebUsingHttp(command, object);
	}

	@Override
	public void edit(Food object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/editFood";
		editUsingHttp(command, object);
	}

	@Override
	public void delete(Food object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/deleteFood";
		deleteUsingHttp(command, object);
	}

	@Override
	public List<Food> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllFood";
		Type type = new TypeToken<List<Food>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

}
