package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.web.dao.WebBloodSugarDao;
import com.google.gson.reflect.TypeToken;

public class WebBloodSugarDaoImpl extends GenericWebTrackerDaoImpl<BloodSugar>
		implements WebBloodSugarDao {

	@Override
	public int add_ReturnEntryIdInWeb(BloodSugar bloodSugar)
			throws WebServerException, OutdatedAccessTokenException {
		String command = "tracker/addBloodSugar";
		return add_ReturnEntryIDToWebUsingHttp(command, bloodSugar);
	}

	@Override
	public void edit(BloodSugar object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/editBloodSugar";
		editUsingHttp(command, object);
	}

	@Override
	public void delete(BloodSugar object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/deleteBloodSugar";
		deleteUsingHttp(command, object);
	}

	@Override
	public List<BloodSugar> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllBloodSugar";
		Type type = new TypeToken<List<BloodSugar>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

}
