package com.example.phr.web.daoimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.tools.GSONConverter;
import com.example.phr.tools.JSONRequestCreator;
import com.example.phr.web.dao.UserDao;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public abstract class BasicDaoImpl<T> {
	UserDao userDao;
	JSONRequestCreator jsonRequestCreator;

	public BasicDaoImpl() {
		userDao = new UserDaoImpl();
		jsonRequestCreator = new JSONRequestCreator();
	}

	protected String performHttpRequest_String(String command,
			String jsonStringParams) throws WebServerException {
		String address = "http://10.0.2.2:8080/PHR-WebServer/" + command;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(address);
			StringEntity se = new StringEntity(jsonStringParams);
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			out.close();
			response.getEntity().writeTo(out);
			return out.toString();

		} catch (IOException e) {
			for (StackTraceElement s : e.getStackTrace()) {
				System.out.println(s.toString());
			}
			System.out.println(e.getMessage());
			throw new WebServerException("Error in HTTP", e);
		}
	}

	protected JSONObject performHttpRequest_JSON(String command,
			String jsonStringParams) throws WebServerException {
		try {
			System.out.println("Umabot hanggang sa perform Http Request");
			JSONObject response = new JSONObject(performHttpRequest_String(
					command, jsonStringParams));
			return response;
		} catch (JSONException e) {
			throw new WebServerException("Cannot convert JSON object", e);
		}
	}

	protected <T> T getGSONObject(String response, Class<T> cls)
			throws WebServerException {
		try {
			Gson gson = new Gson();
			return gson.fromJson(response, cls);
		} catch (JsonSyntaxException e) {
			throw new WebServerException("Error in GSON", e);
		}
	}

	public int add_ReturnIDToWebUsingHttp(String command, String fieldName,
			T object) throws WebServerException, OutdatedAccessTokenException {
		try {
			JSONObject data = new JSONObject();
			data.put("accessToken", userDao.getAccessToken().getAccessToken());
			data.put("username", userDao.getAccessToken().getUserName());
			data.put(fieldName, GSONConverter.convertObjectToJSON(object));
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
}
