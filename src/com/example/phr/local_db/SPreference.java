package com.example.phr.local_db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SPreference {
	Context c;

	public SPreference(Context c) {
		this.c = c;
	}

	/**
	 * Method used to get Shared Preferences
	 */

	public SharedPreferences getPreferences() {
		return c.getSharedPreferences("HealthGemPreferences", c.MODE_PRIVATE);
	}

	/**
	 * Method used to save Preferences
	 */
	public void savePreferences(String key, String value) {
		SharedPreferences sharedPreferences = getPreferences();
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * Method used to load Preferences
	 */
	public String loadPreferences(String key) {
		try {
			SharedPreferences sharedPreferences = getPreferences();
			String strSavedMemo = sharedPreferences.getString(key, "");
			return strSavedMemo;
		} catch (NullPointerException nullPointerException) {
			Log.e("Error caused at  TelaSketchUtin loadPreferences method",
					">======>" + nullPointerException);
			return null;
		}
	}

	/**
	 * Method used to delete Preferences
	 */
	public boolean deletePreferences(String key) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.remove(key).commit();
		return false;
	}
}
