package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.AccessDao;
import com.example.phr.mobile.daoimpl.AccessDaoImpl;
import com.example.phr.tools.GSONConverter;

public abstract class GenericWebTrackerDaoImpl<TrackerEntry> extends
		BasicDaoImpl {

	AccessDao accessDao = new AccessDaoImpl();

	public int add_ReturnEntryIDToWebUsingHttp(String command,
			TrackerEntry object) throws WebServerException,
			OutdatedAccessTokenException {
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", accessDao.getAccessToken());
			data.put("objectToAdd", GSONConverter.convertObjectToJSON(object));
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

	public void editUsingHttp(String command, TrackerEntry object)
			throws WebServerException, OutdatedAccessTokenException {
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", accessDao.getAccessToken().getAccessToken());
			data.put("objectToEdit", GSONConverter.convertObjectToJSON(object));
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
				System.out.println("Editing successful");

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

	public void deleteUsingHttp(String command, TrackerEntry object)
			throws WebServerException, OutdatedAccessTokenException {
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", accessDao.getAccessToken().getAccessToken());
			data.put("objectToDelete",
					GSONConverter.convertObjectToJSON(object));
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

	public List<TrackerEntry> getAllUsingHttp(String command, Type type)
			throws WebServerException, OutdatedAccessTokenException {
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", accessDao.getAccessToken().getAccessToken());
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
				String list = response.getJSONObject("data").getString("list");
				return GSONConverter.convertJSONToObjectList(list, type);
			} else {
				throw new WebServerException(
						"An error has occurred while communicating"
								+ "with the web server.");
			}
		} catch (JSONException e) {
			throw new WebServerException("Error has occurred", e);
		}
	}
}
