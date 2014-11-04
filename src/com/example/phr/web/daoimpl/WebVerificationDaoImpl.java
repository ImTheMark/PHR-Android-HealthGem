package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.dao.MobileAccessDao;
import com.example.phr.mobile.daoimpl.MobileAccessDaoImpl;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.tools.GSONConverter;
import com.example.phr.web.dao.WebVerificationDao;
import com.google.gson.reflect.TypeToken;

public class WebVerificationDaoImpl extends BasicDaoImpl implements
		WebVerificationDao {

	MobileAccessDao accessDao = new MobileAccessDaoImpl();

	@Override
	public void updateListOfUnverifiedPosts()
			throws OutdatedAccessTokenException, WebServerException {
		String command = "verification/addNewPosts";
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", accessDao.getAccessToken().getAccessToken());
			data.put("username", accessDao.getAccessToken().getUserName());
			data.put("fbAccessToken", HealthGem.getSharedPreferences()
					.loadPreferences(SPreference.FBACCESSTOKEN)); // TO CHANGE
			String jsonToSend = jsonRequestCreator
					.createJSONRequest(data, null);
			System.out.println("" + "JSON Request Sent: " + jsonToSend);
			JSONObject response = performHttpRequest_JSON(command, jsonToSend);
			System.out.println("JSON Response Received: " + response);

			if (response.getJSONObject("data").has("isValidAccessToken")
					&& response.getJSONObject("data")
							.getString("isValidAccessToken").equals("false")) {
				throw new OutdatedAccessTokenException(
						"The access token used in the request is outdated, please ask the user to log in again.");
			} else if (response.get("status").equals("success")) {
				;
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
	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts()
			throws WebServerException, OutdatedAccessTokenException {
		String command = "verification/getAllFoods";
		Type type = new TypeToken<List<UnverifiedFoodEntry>>() {
		}.getType();
		return getAll(command, type);
	}

	@Override
	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts()
			throws WebServerException, OutdatedAccessTokenException {
		String command = "verification/getAllActivities";
		Type type = new TypeToken<List<UnverifiedActivityEntry>>() {
		}.getType();
		return getAll(command, type);
	}

	@Override
	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts()
			throws WebServerException, OutdatedAccessTokenException {
		String command = "verification/getAllRestaurants";
		Type type = new TypeToken<List<UnverifiedRestaurantEntry>>() {
		}.getType();
		return getAll(command, type);
	}

	@Override
	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts()
			throws WebServerException, OutdatedAccessTokenException {
		String command = "verification/getAllSportsEstablishments";
		Type type = new TypeToken<List<UnverifiedSportsEstablishmentEntry>>() {
		}.getType();
		return getAll(command, type);
	}

	private <T> List<T> getAll(String command, Type type)
			throws WebServerException, OutdatedAccessTokenException {
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", accessDao.getAccessToken().getAccessToken());
			data.put("username", accessDao.getAccessToken().getUserName());
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
				JSONArray array = response.getJSONObject("data").getJSONArray(
						"array");
				return GSONConverter.convertJSONArrayToObjectList(array, type);
			} else {
				throw new WebServerException(
						"An error has occurred while communicating"
								+ "with the web server.");
			}
		} catch (JSONException e) {
			throw new WebServerException("Error has occurred", e);
		}
	}

	@Override
	public void delete(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException {
		String command = "verification/deleteUnverifiedFood";
		delete(command, entry);
	}

	@Override
	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException {
		String command = "verification/deleteUnverifiedActivity";
		delete(command, entry);
	}

	@Override
	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException {
		String command = "verification/deleteUnverifiedRestaurant";
		delete(command, entry);
	}

	@Override
	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException {
		String command = "verification/deleteUnverifiedSportsEstablishment";
		delete(command, entry);
	}

	private void delete(String command, Object object)
			throws WebServerException, OutdatedAccessTokenException {
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", accessDao.getAccessToken().getAccessToken());
			data.put("username", accessDao.getAccessToken().getUserName());
			data.put("objectToDelete",
					GSONConverter.convertObjectToJSON(object));
			String jsonToSend = jsonRequestCreator
					.createJSONRequest(data, null);
			System.out.println("JSON Request Sent: " + jsonToSend);
			JSONObject response = performHttpRequest_JSON(command, jsonToSend);
			System.out.println("JSON Response Received: " + response);

			if (response.has("data")
					&& response.getJSONObject("data").has("isValidAccessToken")
					&& response.getJSONObject("data")
							.getString("isValidAccessToken").equals("false")) {
				throw new OutdatedAccessTokenException(
						"The access token used in the request is outdated, please ask the user to log in again.");
			} else if (response.get("status").equals("success")) {
				System.out.println("Deleting successful");
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
}
