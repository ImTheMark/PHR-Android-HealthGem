package com.example.phr.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class HealthGem extends Application {

	private static HealthGem healthGem;
	private static Context context;
	//private static SharedPreferences mSharedPreferences;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		HealthGem.healthGem = this;
		
		if (context == null) {
			context = this.getApplicationContext();
		}
		
	}

/*	public static SharedPreferences getSharedPreferences() {
		if (mSharedPreferences == null) {

			mSharedPreferences = getContext().getSharedPreferences(
					"HealthGem SharedPreference", 0);
		}

		return mSharedPreferences;
	}*/
	
	public static HealthGem getInstance() {
		return healthGem;
	}
	
	public static Context getContext() {
		return context;
	}
	
}
