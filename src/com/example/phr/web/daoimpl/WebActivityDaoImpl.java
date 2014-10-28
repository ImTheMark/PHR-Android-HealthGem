package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.web.dao.WebActivityDao;
import com.google.gson.reflect.TypeToken;

public class WebActivityDaoImpl extends GenericListsFetcherDaoImpl<Activity>
		implements WebActivityDao {

	@Override
	public int addReturnEntryId(Activity activity) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "activitylist/add";
		return addReturnEntryIdUsingHttp(command, activity);
	}

	@Override
	public List<Activity> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "activitylist/getAll";
		Type type = new TypeToken<List<Activity>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

	@Override
	public List<Activity> search(String query) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "activitylist/search";
		Type type = new TypeToken<List<Activity>>() {
		}.getType();
		return searchUsingHttp(command, type, query);
	}

}
