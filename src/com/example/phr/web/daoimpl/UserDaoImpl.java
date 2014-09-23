package com.example.phr.web.daoimpl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.phr.exceptions.UserAlreadyExistsException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.AccessDao;
import com.example.phr.mobile.daoimpl.AccessDaoImpl;
import com.example.phr.model.AccessToken;
import com.example.phr.model.User;
import com.example.phr.tools.GSONConverter;
import com.example.phr.tools.JSONRequestCreator;
import com.example.phr.web.dao.UserDao;

public class UserDaoImpl extends BasicDaoImpl implements UserDao {

	private JSONRequestCreator jsonRequestCreator;
	private AccessDao accessDao;

	public UserDaoImpl() {
		jsonRequestCreator = new JSONRequestCreator();
		accessDao = new AccessDaoImpl();
	}

	@Override
	public void registerUser(User user) throws WebServerException,
			UserAlreadyExistsException {

		try {
			String command = "user/register";
			JSONObject userJSON = GSONConverter.convertObjectToJSON(user);

			String jsonToSend = jsonRequestCreator.createJSONRequest(userJSON,
					null);
			System.out.println("JSON Request Sent: " + jsonToSend);
			JSONObject response = performHttpRequest_JSON(command, jsonToSend);
			System.out.println("JSON Response Received: " + response);
			if (response.get("status").equals("fail"))
				throw new WebServerException(
						"An error has occurred while communicating"
								+ "with the web server.");
			else if (response.getJSONObject("data")
					.has("usernameAlreadyExists")
					&& response.getJSONObject("data")
							.get("usernameAlreadyExists").equals("true")) {
				System.out.println("Username already exists");
				throw new UserAlreadyExistsException(
						"User already exists in the database");
			} else {
				String userAccessToken = response.getJSONObject("data")
						.getString("userAccessToken");
				System.out.println("User Access Token Received "
						+ userAccessToken);
				accessDao.setAccessToken(new AccessToken(userAccessToken, user
						.getUsername()));
				System.out.println("Stored Access Token: "
						+ accessDao.getAccessToken().getAccessToken()
						+ " and Username: "
						+ accessDao.getAccessToken().getUserName());
			}
		} catch (JSONException e) {
			throw new WebServerException("Error in parsing JSON", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validateUser(String username, String password)
			throws WebServerException {

		try {
			String command = "user/validateLogin";

			Map<String, String> map = new HashMap<String, String>();
			map.put("username", username);

			map.put("hashedPassword", password);

			String jsonToSend = jsonRequestCreator.createJSONRequest(map, null);
			System.out.println("JSON Request Sent: " + jsonToSend);
			JSONObject response = performHttpRequest_JSON(command, jsonToSend);
			System.out.println("JSON Response Received: " + response);
			if (response.get("status").equals("fail")) {
				System.out.println("Failed From Status");
				throw new WebServerException(
						"An error has occurred while communicating"
								+ "with the web server.");
			}
			if (response.getJSONObject("data").get("isValid").equals("true")) {
				String userAccessToken = response.getJSONObject("data")
						.getString("userAccessToken");
				System.out.println("User Access Token Received: "
						+ userAccessToken);
				accessDao.setAccessToken(new AccessToken(userAccessToken,
						username));
				System.out.println("Stored Access Token: "
						+ accessDao.getAccessToken().getAccessToken()
						+ " and Username: "
						+ accessDao.getAccessToken().getUserName());
				return true;
			} else if (response.getJSONObject("data").get("isValid")
					.equals("false")) {
				return false;
			}
			System.out.println("Failed Last If");
			throw new WebServerException(
					"An error has occurred while communicating"
							+ "with the web server.");
		} catch (JSONException e) {
			throw new WebServerException("An error has occurred while parsing"
					+ "the web server response.");
		}
	}

	@Override
	public User getUserGivenUsername(String username) throws WebServerException {
		return null;
	}

}
