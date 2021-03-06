package com.example.phr.local_db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SPreference {

	public static final String REGISTER_USERNAME = "rUsername";
	public static final String REGISTER_PASSWORD = "rPassword";
	public static final String REGISTER_NAME = "rName";
	public static final String REGISTER_CONTACTNUMBER = "rNumber";
	public static final String REGISTER_EMAIL = "rEmail";
	public static final String REGISTER_BIRTHDATE = "rBirthdate";
	public static final String REGISTER_GENDER = "rGender";
	public static final String REGISTER_HEIGHTINCHES = "rHeight";
	public static final String REGISTER_WEIGHTPOUNDS = "rWeight";
	public static final String REGISTER_CONTACTPERSON = "rContactPerson";
	public static final String REGISTER_CONTACTPERSONNUMBER = "rContactPersonNumber";
	public static final String REGISTER_ALLERGIES = "rAllergies";
	public static final String REGISTER_KNOWNHEALTHPROBLEMS = "rKnown";
	public static final String REGISTER_FBUSERNAME = "rFBUsername";
	public static final String REGISTER_FBACCESSTOKEN = "rFBAccessToken";

	public static final String USERID = "UserID";
	public static final String USERNAME = "Username";
	public static final String NAME = "Name";
	public static final String NUMBER = "Number";
	public static final String EMAIL = "Email";
	public static final String BIRTHDATE = "Birthdate";
	public static final String GENDER = "Gender";
	public static final String HEIGHTINCHES = "Height";
	public static final String CONTACTPERSON = "ContactPerson";
	public static final String CONTACTPERSONNUMBER = "ContactPersonNumber";
	public static final String ALLERGIES = "Allergies";
	public static final String KNOWNHEALTHPROBLEMS = "Known";
	public static final String FBUSERNAME = "FBUsername";
	public static final String FBACCESSTOKEN = "FBAccessToken";
	public static final String PROFILE_PICTURE = "ProfilePicture";

	public static final String SETTINGS_WEIGHTUNIT = "weightUnit";
	public static final String SETTINGS_HEIGHTUNIT = "heightUnit";
	public static final String SETTINGS_NOTIF_BLOODPRESSURE = "notifBloodPressure";
	public static final String SETTINGS_NOTIF_BLOODSUGAR = "notifBloodSugar";
	
	public static final String SETTINGS_TUTORIAL = "help";

	public static final String VERIFICATION_COUNT = "unverifiedPostsCount";
	public static final String VERIFICATION_TEMP_IMAGE = "verificationTemporaryImage";

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
