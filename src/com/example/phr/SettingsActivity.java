package com.example.phr;

import com.example.phr.local_db.DatabaseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends Activity {
	
	private RadioGroup radioGroupWeight;
	private RadioGroup radioGroupHeight;
	private Button logoutButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setTitle("Settings");
		radioGroupHeight = (RadioGroup) findViewById(R.id.radioGroupSettingHeight);
		radioGroupWeight = (RadioGroup) findViewById(R.id.radioGroupSettingWeight);
		logoutButton = (Button) findViewById(R.id.btnLogout);
		
		logoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DatabaseHandler.getDBHandler().deleteAccessToken();
			}
		});
		
		
		setTitle("Settings");
	}

	private String getItemSelectedRadioGroupWeight() {
		int selected = radioGroupWeight.getCheckedRadioButtonId();
        Button radioBtn = (RadioButton) findViewById(selected);
        return radioBtn.getText().toString();
	}

	private String getItemSelectedRadioGroupHeight() {
		int selected = radioGroupHeight.getCheckedRadioButtonId();
        Button radioBtn = (RadioButton) findViewById(selected);
        return radioBtn.getText().toString();
	}

}