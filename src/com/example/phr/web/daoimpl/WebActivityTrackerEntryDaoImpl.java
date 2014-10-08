package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.ActivitySingle;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.web.dao.WebActivityDao;
import com.google.gson.reflect.TypeToken;

public class WebActivityTrackerEntryDaoImpl extends
		GenericWebTrackerDaoImpl<ActivityTrackerEntry> implements WebActivityDao {

	@Override
	public int add_ReturnEntryIdInWeb(ActivityTrackerEntry object)
			throws WebServerException, OutdatedAccessTokenException {
		String command = "tracker/addActivityTrackerEntry";
		return add_ReturnEntryIDToWebUsingHttp(command, object);
	}

	@Override
	public void edit(ActivityTrackerEntry object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/editActivityTrackerEntry";
		editUsingHttp(command, object);
	}

	@Override
	public void delete(ActivityTrackerEntry object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/deleteActivityTrackerEntry";
		deleteUsingHttp(command, object);
	}

	@Override
	public List<ActivityTrackerEntry> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllActivityTrackerEntry";
		Type type = new TypeToken<List<ActivityTrackerEntry>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

}
