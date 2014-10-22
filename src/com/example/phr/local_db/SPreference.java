package com.example.phr.local_db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SPreference {
	
	public static final String REGISTER_USERNAME = "rUsername";
	public static final String REGISTER_FIRSTNAME = "rFirst";
	public static final String REGISTER_MIDDLENAME = "rMiddle";
	public static final String REGISTER_LASTNAME = "rLast";
	public static final String REGISTER_BIRTHDATE = "rBirthdate";
	public static final String REGISTER_GENDER = "rGender";
	public static final String REGISTER_HEIGHT = "rHeight";
	public static final String REGISTER_WEIGHT = "rWeight";
	public static final String REGISTER_CONTACTPERSON = "rContactPerson";
	public static final String REGISTER_CONTACTPERSONNUMBER = "rContactPersonNumber";
	public static final String REGISTER_ALLERGIES = "rAllergies";
	public static final String REGISTER_KNOWNHEALTHPROBLEMS = "rKnown";
	public static final String REGISTER_FBUSERNAME = "rFBUsername";
	public static final String REGISTER_FBACCESSTOKEN = "rFBAccessToken";
	
	public static final String USERNAME = "Username";
	public static final String FIRSTNAME = "First";
	public static final String MIDDLENAME = "Middle";
	public static final String LASTNAME = "Last";
	public static final String BIRTHDATE = "Birthdate";
	public static final String GENDER = "Gender";
	public static final String HEIGHT = "Height";
	public static final String WEIGHT = "Weight";
	public static final String CONTACTPERSON = "ContactPerson";
	public static final String CONTACTPERSONNUMBER = "ContactPersonNumber";
	public static final String ALLERGIES = "Allergies";
	public static final String KNOWNHEALTHPROBLEMS = "Known";
	public static final String FBUSERNAME = "FBUsername";
	public static final String FBACCESSTOKEN = "FBAccessToken";
	
	
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
	
	public void clearRegisterInformation(){
		savePreferences(REGISTER_ALLERGIES,"");
		savePreferences(REGISTER_BIRTHDATE,"");
		savePreferences(REGISTER_CONTACTPERSON,"");
		savePreferences(REGISTER_CONTACTPERSONNUMBER,"");
		savePreferences(REGISTER_FBACCESSTOKEN,"");
		savePreferences(REGISTER_FBUSERNAME,"");
		savePreferences(REGISTER_FIRSTNAME,"");
		savePreferences(REGISTER_GENDER,"");
		savePreferences(REGISTER_HEIGHT,"");
		savePreferences(REGISTER_KNOWNHEALTHPROBLEMS,"");
		savePreferences(REGISTER_LASTNAME,"");
		savePreferences(REGISTER_MIDDLENAME,"");
		savePreferences(REGISTER_USERNAME,"");
		savePreferences(REGISTER_WEIGHT,"");
	}

	public void registerUser() {
		savePreferences(ALLERGIES, loadPreferences(REGISTER_ALLERGIES));
		savePreferences(BIRTHDATE, loadPreferences(REGISTER_BIRTHDATE));
		savePreferences(CONTACTPERSON, loadPreferences(REGISTER_CONTACTPERSON));
		savePreferences(CONTACTPERSONNUMBER, loadPreferences(REGISTER_CONTACTPERSONNUMBER));
		savePreferences(FBACCESSTOKEN, loadPreferences(REGISTER_FBACCESSTOKEN));
		savePreferences(FBUSERNAME, loadPreferences(REGISTER_FBUSERNAME));
		savePreferences(FIRSTNAME, loadPreferences(REGISTER_FIRSTNAME));
		savePreferences(GENDER, loadPreferences(REGISTER_GENDER));
		savePreferences(HEIGHT, loadPreferences(REGISTER_HEIGHT));
		savePreferences(KNOWNHEALTHPROBLEMS, loadPreferences(REGISTER_KNOWNHEALTHPROBLEMS));
		savePreferences(LASTNAME, loadPreferences(REGISTER_LASTNAME));
		savePreferences(MIDDLENAME, loadPreferences(REGISTER_MIDDLENAME));
		savePreferences(USERNAME, loadPreferences(REGISTER_USERNAME));
		savePreferences(WEIGHT, loadPreferences(REGISTER_WEIGHT));
		
		clearRegisterInformation();
	}
}
