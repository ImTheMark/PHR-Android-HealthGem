package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.ActivitySingle;
import com.example.phr.model.Activity;
import com.example.phr.web.dao.WebActivityDao;
import com.google.gson.reflect.TypeToken;

public class WebActivityDaoImpl extends
		GenericListsFetcherDaoImpl<ActivitySingle> implements WebActivityDao {

	@Override
	public int addReturnEntryId(ActivitySingle activity)
			throws WebServerException, OutdatedAccessTokenException {
		String command = "activitylist/add";
		return addReturnEntryIdUsingHttp(command, activity);
	}

	@Override
	public List<ActivitySingle> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "activitylist/getAll";
		Type type = new TypeToken<List<Activity>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

	@Override
	public List<ActivitySingle> search(String query) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "activitylist/search";
		Type type = new TypeToken<List<Activity>>() {
		}.getType();
		return searchUsingHttp(command, type, query);
	}

}
