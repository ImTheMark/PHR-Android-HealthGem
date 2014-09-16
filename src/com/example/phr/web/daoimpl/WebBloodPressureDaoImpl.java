package com.example.phr.web.daoimpl;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.tools.GSONConverter;
import com.example.phr.tools.JSONRequestCreator;
import com.example.phr.web.dao.UserDao;
import com.example.phr.web.dao.WebBloodPressureDao;

public class WebBloodPressureDaoImpl extends BasicDaoImpl implements
		WebBloodPressureDao {

	UserDao userDao;
	JSONRequestCreator jsonRequestCreator;

	public WebBloodPressureDaoImpl() {
		userDao = new UserDaoImpl();
		jsonRequestCreator = new JSONRequestCreator();
	}

	@Override
	public int add_ReturnEntryIdInWeb(BloodPressure bloodPressure)
			throws WebServerException, OutdatedAccessTokenException {
		String command = "tracker/addBloodPressure";

		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", userDao.getAccessToken().getAccessToken());
			data.put("username", userDao.getAccessToken().getUserName());
			data.put("bloodPressure",
					GSONConverter.convertObjectToJSON(bloodPressure));
			String jsonToSend = jsonRequestCreator
					.createJSONRequest(data, null);
			System.out.println("JSON Request Sent: " + jsonToSend);
			JSONObject response = performHttpRequest_JSON(command, jsonToSend);
			System.out.println("JSON Response Received: " + response);

			if (response.getJSONObject("data").has("isValidAccessToken")
					&& response.getJSONObject("data")
							.getString("isValidAccessToken").equals("false")) {
				throw new OutdatedAccessTokenException(
						"The access token used in the request is outdated, please ask the user to log in again.");
			} else if (response.get("status").equals("success")) {
				int entryID = response.getJSONObject("data").getInt("entryID");
				return entryID;
			} else {
				throw new WebServerException(
						"An error has occurred while communicating"
								+ "with the web server.");
			}

		} catch (JSONException e) {
			throw new WebServerException(
					"An error has occurred while communicating"
							+ "with the web server.");
		}

	}

	@Override
	public void edit(BloodPressure object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BloodPressure object) {
		// TODO Auto-generated method stub

	}

	@Override
	public BloodPressure get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BloodPressure> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
