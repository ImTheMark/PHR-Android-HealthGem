package com.example.phr;

import java.text.ParseException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.dao.MobileSettingsDao;
import com.example.phr.mobile.daoimpl.MobileSettingsDaoImpl;
import com.example.phr.mobile.models.User;
import com.example.phr.service.UserService;
import com.example.phr.serviceimpl.UserServiceImpl;
import com.example.phr.tools.DateTimeParser;

@SuppressLint("NewApi")
public class RegisterUserInformationActivity extends Activity {

	EditText fullName;
	EditText contactNumber;
	EditText email;
	EditText height;
	EditText weight;
	EditText contactPerson;
	EditText contactPersonNumber;
	EditText allergies;
	EditText knownHealthProblems;
	EditText birthdate;
	Spinner heightUnit;
	Spinner weightUnit;
	Spinner gender;
	MobileSettingsDao settingsDao;
	Boolean isRegister = true;
	UserService userService;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		userService = new UserServiceImpl();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_registration_user_information);

		settingsDao = new MobileSettingsDaoImpl();

		heightUnit = (Spinner) findViewById(R.id.dropdownRegistrationHeight);
		weightUnit = (Spinner) findViewById(R.id.dropdownRegistrationWeight);

		fullName = (EditText) findViewById(R.id.editTextRegistrationFullName);
		contactNumber = (EditText) findViewById(R.id.editTextRegistrationContactNumber);
		email = (EditText) findViewById(R.id.editTextRegistrationEmailAddress);
		height = (EditText) findViewById(R.id.txtRegistrationHeightInput);
		weight = (EditText) findViewById(R.id.txtRegistrationWeightInput);
		contactPerson = (EditText) findViewById(R.id.EditTextRegistrationEmergencyContactPerson);
		contactPersonNumber = (EditText) findViewById(R.id.EditTextRegistrationEmergencyContactNumber);
		allergies = (EditText) findViewById(R.id.EditTextRegistrationAllergies);
		knownHealthProblems = (EditText) findViewById(R.id.EditTextRegistrationHealthProblems);
		gender = (Spinner) findViewById(R.id.textViewRegistrationGender);
		birthdate = (EditText) findViewById(R.id.editTextRegistrationBirthDate);

		fullName.setText(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.REGISTER_NAME));
		contactNumber.setText(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.REGISTER_CONTACTNUMBER));
		email.setText(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.REGISTER_EMAIL));
		height.setText(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.REGISTER_HEIGHT));
		weight.setText(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.REGISTER_WEIGHT));
		contactPerson.setText(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.REGISTER_CONTACTPERSON));
		contactPersonNumber.setText(HealthGem.getSharedPreferences()
				.loadPreferences(SPreference.REGISTER_CONTACTPERSONNUMBER));
		allergies.setText(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.REGISTER_ALLERGIES));
		knownHealthProblems.setText(HealthGem.getSharedPreferences()
				.loadPreferences(SPreference.REGISTER_KNOWNHEALTHPROBLEMS));
		birthdate.setText(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.REGISTER_BIRTHDATE));

		Resources res = getResources();
		String[] genderArray = res.getStringArray(R.array.gender);

		if (HealthGem.getSharedPreferences()
				.loadPreferences(SPreference.REGISTER_GENDER)
				.equals(genderArray[0]))
			gender.setSelection(0);
		else
			gender.setSelection(1);

		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();

		if (extras != null && in.hasExtra("mode")) {
			isRegister = in.getExtras().getBoolean("mode");
			User currUser = userService.getUser();
			fullName.setText(currUser.getName());
			contactNumber.setText(currUser.getContactNumber());
			email.setText(currUser.getEmail());
			height.setText(currUser.getHeight() + "");
			weight.setText(currUser.getWeight() + "");
			contactPerson.setText(currUser.getEmergencyPerson());
			contactPersonNumber.setText(currUser.getEmergencyContactNumber());
			allergies.setText(currUser.getAllergies());
			knownHealthProblems.setText(currUser.getKnownHealthProblems());
			birthdate.setText((currUser.getDateOfBirth() + "")
					.subSequence(0, 9));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (isRegister)
			getMenuInflater().inflate(R.menu.menu_registration_next, menu);
		else
			getMenuInflater().inflate(R.menu.menu_registration_save, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_next:
			if (fullName.getText().toString().equals("")
					|| contactNumber.getText().toString().equals("")
					|| email.getText().toString().equals("")
					|| height.getText().toString().equals("")
					|| weight.getText().toString().equals("")
					|| contactPerson.getText().toString().equals("")
					|| contactPersonNumber.getText().toString().equals("")
					|| birthdate.getText().toString().equals("")) {
				Toast.makeText(HealthGem.getContext(),
						"Please complete the missing fields.",
						Toast.LENGTH_LONG).show();
			} else {
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_NAME,
						fullName.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_CONTACTNUMBER,
						contactNumber.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_EMAIL, email.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_HEIGHT,
						height.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_WEIGHT,
						weight.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_CONTACTPERSON,
						contactPerson.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_CONTACTPERSONNUMBER,
						contactPersonNumber.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_ALLERGIES,
						allergies.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_KNOWNHEALTHPROBLEMS,
						knownHealthProblems.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_GENDER,
						gender.getSelectedItem() + "");
				HealthGem.getSharedPreferences().savePreferences(
						SPreference.REGISTER_BIRTHDATE,
						birthdate.getText().toString() + " 00:00:00");

				if (heightUnit.getSelectedItemPosition() == 0)
					settingsDao.setHeightToFeet();
				else
					settingsDao.setHeightToCentimeters();

				if (weightUnit.getSelectedItemPosition() == 0)
					settingsDao.setWeightToPounds();
				else
					settingsDao.setWeightToKilograms();

				Intent intent = new Intent(getApplicationContext(),
						RegisterFBLoginActivity.class);
				startActivity(intent);
			}
			return true;
		case R.id.menu_item_save:
			if (fullName.getText().toString().equals("")
					|| contactNumber.getText().toString().equals("")
					|| email.getText().toString().equals("")
					|| height.getText().toString().equals("")
					|| weight.getText().toString().equals("")
					|| contactPerson.getText().toString().equals("")
					|| contactPersonNumber.getText().toString().equals("")
					|| birthdate.getText().toString().equals("")) {
				Toast.makeText(HealthGem.getContext(),
						"Please complete the missing fields.",
						Toast.LENGTH_LONG).show();
			} else {
				User editedUser = userService.getUser();

				editedUser.setName(fullName.getText().toString());
				editedUser.setContactNumber(contactNumber.getText().toString());
				editedUser.setEmail(email.getText().toString());
				editedUser.setHeight(Double.parseDouble(height.getText()
						.toString()));
				editedUser.setWeight(Double.parseDouble(weight.getText()
						.toString()));
				editedUser.setEmergencyPerson(contactPerson.getText()
						.toString());
				editedUser.setEmergencyContactNumber(contactPersonNumber
						.getText().toString());
				editedUser.setAllergies(allergies.getText().toString());
				editedUser.setKnownHealthProblems(knownHealthProblems.getText()
						.toString());
				editedUser.setGender(gender.getSelectedItem() + "");
				try {
					editedUser.setDateOfBirth(DateTimeParser
							.getTimestamp(birthdate.getText().toString()
									+ " 00:00:00"));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (heightUnit.getSelectedItemPosition() == 0)
					settingsDao.setHeightToFeet();
				else
					settingsDao.setHeightToCentimeters();

				if (weightUnit.getSelectedItemPosition() == 0)
					settingsDao.setWeightToPounds();
				else
					settingsDao.setWeightToKilograms();

				try {
					userService.edit(editedUser);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OutdatedAccessTokenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
