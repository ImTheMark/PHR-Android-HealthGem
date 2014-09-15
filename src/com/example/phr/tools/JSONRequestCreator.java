package com.example.phr.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONRequestCreator {

	public JSONRequestCreator() {
	}

	public String createJSONRequest(Object object, String message)
			throws JSONException {
		JSONObject json = new JSONObject();

		if (object.getClass().equals(JSONObject.class))
			json.put("data", object);
		else
			json.put("data", GSONConverter.convertObjectToJSON(object));
		json.put("message", message);

		return json.toString();
	}
}
