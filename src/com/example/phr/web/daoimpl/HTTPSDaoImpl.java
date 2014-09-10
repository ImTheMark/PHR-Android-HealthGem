package com.example.phr.web.daoimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.phr.exceptions.WebServerException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public abstract class HTTPSDaoImpl {

	protected String performHttpRequest_String(String command,
			String jsonStringParams) throws WebServerException {
		String address = "https://10.0.2.2:8080/PHR-WebServer/" + command;
		try {
			// Create the HTTP request
			HttpParams httpParameters = new BasicHttpParams();

			// Setup timeouts
			HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
			HttpConnectionParams.setSoTimeout(httpParameters, 15000);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httppost = new HttpPost(address);
			StringEntity se = new StringEntity(jsonStringParams);
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			httppost.setEntity(se);
			HttpResponse response = httpclient.execute(httppost);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			out.close();
			response.getEntity().writeTo(out);
			return out.toString();

		} catch (IOException e) {
			throw new WebServerException("Error in HTTP", e);
		}
	}

	protected JSONObject performHttpRequest_JSON(String command,
			String jsonStringParams) throws WebServerException {
		try {
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
