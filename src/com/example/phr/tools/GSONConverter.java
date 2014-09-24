package com.example.phr.tools;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class GSONConverter {

	public static <T> T getGSONObjectGivenJsonString(String jsonString,
			Class<T> classTypeToGenerate) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, classTypeToGenerate);
	}

	public static <T> T getGSONObjectGivenJsonObject(JSONObject json,
			Class<T> classTypeToGenerate) {
		Gson gson = new Gson();
		String jsonString = json.toString();
		return gson.fromJson(jsonString, classTypeToGenerate);
	}

	public static JSONObject convertObjectToJSON(Object objectToBeConverted)
			throws JSONException {
		if (objectToBeConverted.getClass().equals(JSONObject.class))
			return (JSONObject) objectToBeConverted;
		Gson gson = new Gson();
		String jsonString = gson.toJson(objectToBeConverted);
		return new JSONObject(jsonString);
	}

	public static <T> List<T> convertJSONToObjectList(String jsonString,
			Type type) {
		Gson gson = new Gson();
		List<T> list = gson.fromJson(jsonString, type);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		return list;
	}
}
