package com.example.phr;

import com.example.phr.local_db.DatabaseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

            	if(DatabaseHandler.getDBHandler().getAccessToken().getAccessToken() != null){
            		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            		startActivity(intent);
            	}
            	else{
            		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            		startActivity(intent);
            	}
            }
        }, 3000);
	}

}
