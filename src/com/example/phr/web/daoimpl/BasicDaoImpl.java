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

import com.example.phr.exceptions.WebServerException;
import com.example.phr.tools.JSONRequestCreator;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public abstract class BasicDaoImpl {

	JSONRequestCreator jsonRequestCreator = new JSONRequestCreator();

	protected String performHttpRequest_String(String command,
			String jsonStringParams) throws WebServerException {
		String address = "http://192.168.1.102:8080/PHR-WebServer/" + command;
		// String address = "http://10.0.2.2:8080/PHR-WebServer/" + command;
		// String address = "http://192.168.56.101:8080/PHR-WebServer/" +
		// command;
		// String address = "http://10.0.3.2/PHR-WebServer/" + command;
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

}
