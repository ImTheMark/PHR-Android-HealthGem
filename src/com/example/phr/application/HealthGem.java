package com.example.phr.application;

import com.example.phr.local_db.SPreference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class HealthGem extends Application {

	private static HealthGem healthGem;
	private static Context context;
	private static SPreference mSharedPreferences;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		HealthGem.healthGem = this;
		
		if (context == null) {
			context = this.getApplicationContext();
		}
		
	}

	public static SPreference getSharedPreferences() {
		if (mSharedPreferences == null) {

			mSharedPreferences = new SPreference(getContext());
		}

		return mSharedPreferences;
	}
	
	public static HealthGem getInstance() {
		return healthGem;
	}
	
	public static Context getContext() {
		return context;
	}
	
}
