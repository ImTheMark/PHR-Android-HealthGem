package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileAccessDao;
import com.example.phr.mobile.daoimpl.MobileAccessDaoImpl;
import com.example.phr.tools.GSONConverter;

public class GenericListsFetcherDaoImpl<T> extends BasicDaoImpl {

	MobileAccessDao accessDao = new MobileAccessDaoImpl();

	public int addReturnEntryIdUsingHttp(String command, T object)
			throws WebServerException, OutdatedAccessTokenException {
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", accessDao.getAccessToken().getAccessToken());
			data.put("username", accessDao.getAccessToken().getUserName());
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

	public List<T> getAllUsingHttp(String command, Type type)
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

	public List<T> searchUsingHttp(String command, Type type, String query)
			throws WebServerException, OutdatedAccessTokenException {
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", accessDao.getAccessToken().getAccessToken());
			data.put("username", accessDao.getAccessToken().getUserName());
			data.put("query", query);
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

	public void deleteUsingHttp(String command, T object)
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
