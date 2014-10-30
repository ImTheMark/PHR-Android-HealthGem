package com.example.phr;

import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileSettingsDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {
	
	private RadioGroup radioGroupWeight;
	private RadioGroup radioGroupHeight;
	private Button logoutButton;
	private ToggleButton bloodPressureButton;
	private ToggleButton bloodSugarButton;
	private MobileSettingsDao settingsDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setTitle("Settings");
		radioGroupHeight = (RadioGroup) findViewById(R.id.radioGroupSettingHeight);
		radioGroupWeight = (RadioGroup) findViewById(R.id.radioGroupSettingWeight);
		logoutButton = (Button) findViewById(R.id.btnLogout);
		bloodPressureButton = (ToggleButton) findViewById(R.id.settingsToggleButtonBloodPressure);
		bloodSugarButton = (ToggleButton) findViewById(R.id.settingsToggleButtonBloodSugar);
		
		logoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DatabaseHandler.getDBHandler().deleteAccessToken();
			}
		});
		
		bloodPressureButton.setChecked(settingsDao.getBloodPressureNotificationSetting());
		bloodSugarButton.setChecked(settingsDao.getBloodSugarNotificationSetting());
		
		bloodPressureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
				
				if(on){
					settingsDao.setBloodPressureNotificationOn();
				}
				else{
					settingsDao.setBloodPressureNotificationOff();
				}
			}
		});
		
		bloodSugarButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
				
				if(on){
					settingsDao.setBloodSugarNotificationOn();
				}
				else{
					settingsDao.setBloodSugarNotificationOff();
				}
			}
		});
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