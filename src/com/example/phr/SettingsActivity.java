package com.example.phr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileSettingsDao;
import com.example.phr.mobile.daoimpl.MobileSettingsDaoImpl;
import com.example.phr.service.UserService;
import com.example.phr.serviceimpl.UserServiceImpl;

@SuppressLint("NewApi")
public class SettingsActivity extends Activity {

	private RadioGroup radioGroupWeight;
	private RadioGroup radioGroupHeight;
	private Button logoutButton;
	private Button fbButton;
	private Button editUser;
	private ToggleButton bloodPressureButton;
	private ToggleButton bloodSugarButton;
	private MobileSettingsDao settingsDao;
	private UserService userService;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_settings);
		setTitle("Settings");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		radioGroupHeight = (RadioGroup) findViewById(R.id.radioGroupSettingHeight);
		radioGroupWeight = (RadioGroup) findViewById(R.id.radioGroupSettingWeight);
		logoutButton = (Button) findViewById(R.id.btnLogout);
		fbButton = (Button) findViewById(R.id.settingsFBLogin);
		editUser = (Button) findViewById(R.id.btnUpdate);
		bloodPressureButton = (ToggleButton) findViewById(R.id.settingsToggleButtonBloodPressure);
		bloodSugarButton = (ToggleButton) findViewById(R.id.settingsToggleButtonBloodSugar);

		editUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						RegisterUserInformationActivity.class);
				intent.putExtra("mode", false);
				startActivity(intent);
			}
		});

		fbButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						RegisterFBLoginActivity.class);
				intent.putExtra("mode", false);
				startActivity(intent);
			}
		});

		settingsDao = new MobileSettingsDaoImpl();

		if (settingsDao.isHeightSettingInFeet())
			radioGroupHeight.check(R.id.radioGroupSettingHeightButtonFT);
		else
			radioGroupHeight.check(R.id.radioGroupSettingHeightButtonM);

		if (settingsDao.isWeightSettingInPounds())
			radioGroupWeight.check(R.id.radioGroupSettingWeightButtonLBS);
		else
			radioGroupWeight.check(R.id.radioGroupSettingWeightButtonKGS);

		radioGroupHeight
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup arg0, int checkedID) {
						if (checkedID == R.id.radioGroupSettingHeightButtonFT) {
							settingsDao.setHeightToFeet();
						} else
							settingsDao.setHeightToCentimeters();
					}
				});

		radioGroupWeight
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup arg0, int checkedID) {
						if (checkedID == R.id.radioGroupSettingWeightButtonLBS)
							settingsDao.setWeightToPounds();
						else
							settingsDao.setWeightToKilograms();
					}
				});

		logoutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				userService = new UserServiceImpl();
				userService.logoutUser();

				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		bloodPressureButton.setChecked(settingsDao
				.getBloodPressureNotificationSetting());
		bloodSugarButton.setChecked(settingsDao
				.getBloodSugarNotificationSetting());

		bloodPressureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();

				if (on) {
					settingsDao.setBloodPressureNotificationOn();
				} else {
					settingsDao.setBloodPressureNotificationOff();
				}
			}
		});

		bloodSugarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();

				if (on) {
					settingsDao.setBloodSugarNotificationOn();
				} else {
					settingsDao.setBloodSugarNotificationOff();
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

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