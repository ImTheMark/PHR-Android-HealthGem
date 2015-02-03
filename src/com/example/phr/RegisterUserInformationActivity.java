package com.example.phr;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.dao.MobileSettingsDao;
import com.example.phr.mobile.daoimpl.MobileSettingsDaoImpl;
import com.example.phr.mobile.models.User;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.UserService;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.UserServiceImpl;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;
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
	WeightTrackerService weightService;
	LinearLayout registrationHeightInputFeetInches;
	EditText heightInches;

	DatePicker datePicker;
	int year;
	int month;
	int day;
	String yyyy = "";
	String mm = "";
	String dd = "";
	Calendar calendar;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		userService = new UserServiceImpl();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_registration_user_information);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A3A47")));

		registrationHeightInputFeetInches = (LinearLayout) findViewById(R.id.registrationHeightInputFeetInches);
		heightInches = (EditText) findViewById(R.id.txtRegistrationHeightInches);

		settingsDao = new MobileSettingsDaoImpl();

		heightUnit = (Spinner) findViewById(R.id.dropdownRegistrationHeight);

		heightUnit.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (position == 0)
					registrationHeightInputFeetInches
							.setVisibility(View.VISIBLE);
				else
					registrationHeightInputFeetInches.setVisibility(View.GONE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

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
		birthdate.setInputType(InputType.TYPE_NULL);
		birthdate.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				showDialog(999);
			}
		});
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);

		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();

		// EDIT MODE

		if (extras != null && in.hasExtra("mode")) {
			isRegister = in.getExtras().getBoolean("mode");

			User currUser = userService.getUser();

			fullName.setText(currUser.getName());
			contactNumber.setText(currUser.getContactNumber());
			email.setText(currUser.getEmail());
			contactPerson.setText(currUser.getEmergencyPerson());
			contactPersonNumber.setText(currUser.getEmergencyContactNumber());
			allergies.setText(currUser.getAllergies());
			knownHealthProblems.setText(currUser.getKnownHealthProblems());

			Log.e("DATE", currUser.getDateOfBirth() + "");

			year = Integer.parseInt((String) (currUser.getDateOfBirth() + "")
					.subSequence(0, 4));
			month = Integer.parseInt((String) (currUser.getDateOfBirth() + "")
					.subSequence(5, 7));
			day = Integer.parseInt((String) (currUser.getDateOfBirth() + "")
					.subSequence(8, 10));

			Log.e("DATEyear", year + "");
			Log.e("DATEmonth", month + "");
			Log.e("DATEday", day + "");

			yyyy = (String) (currUser.getDateOfBirth() + "").subSequence(0, 4);
			mm = (String) (currUser.getDateOfBirth() + "").subSequence(5, 7);
			dd = (String) (currUser.getDateOfBirth() + "").subSequence(8, 10);

			Log.e("DATEyear", yyyy + "");
			Log.e("DATEmonth", mm + "");
			Log.e("DATEday", dd + "");
			// birthdate.setText((currUser.getDateOfBirth() + "").subSequence(0,
			// 9));

			if (currUser.getGender().equals("M"))
				gender.setSelection(0);
			else
				gender.setSelection(1);

			try {
				weightService = new WeightTrackerServiceImpl();
				if (settingsDao.isWeightSettingInPounds()) {
					weightUnit.setSelection(0);
					weight.setText(weightService.getLatest()
							.getWeightInPounds() + "");
				} else {
					weightUnit.setSelection(1);
					weight.setText(weightService.getLatest()
							.getWeightInKilograms() + "");
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				Toast.makeText(HealthGem.getContext(),
						"No Internet Connection !", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}

			if (settingsDao.isHeightSettingInFeet()) {
				registrationHeightInputFeetInches.setVisibility(View.VISIBLE);
				heightUnit.setSelection(0);
				double feet = currUser.getHeight() / 12;
				double inches = currUser.getHeight() % 12;
				height.setText(feet + "");
				heightInches.setText(inches + "");
			}

			else {
				registrationHeightInputFeetInches.setVisibility(View.GONE);
				heightUnit.setSelection(1);
				Double cm = currUser.getHeight() / 0.39370;
				height.setText(cm + "");
			}
		}

		// REGISTER MODE

		else {
			if (HealthGem.getSharedPreferences().getPreferences()
					.contains(SPreference.REGISTER_NAME)) {
				fullName.setText(HealthGem.getSharedPreferences()
						.loadPreferences(SPreference.REGISTER_NAME));
				contactNumber.setText(HealthGem.getSharedPreferences()
						.loadPreferences(SPreference.REGISTER_CONTACTNUMBER));
				email.setText(HealthGem.getSharedPreferences().loadPreferences(
						SPreference.REGISTER_EMAIL));
				contactPerson.setText(HealthGem.getSharedPreferences()
						.loadPreferences(SPreference.REGISTER_CONTACTPERSON));
				contactPersonNumber.setText(HealthGem.getSharedPreferences()
						.loadPreferences(
								SPreference.REGISTER_CONTACTPERSONNUMBER));
				allergies.setText(HealthGem.getSharedPreferences()
						.loadPreferences(SPreference.REGISTER_ALLERGIES));
				knownHealthProblems.setText(HealthGem.getSharedPreferences()
						.loadPreferences(
								SPreference.REGISTER_KNOWNHEALTHPROBLEMS));

				if (settingsDao.isWeightSettingInPounds()) {
					weightUnit.setSelection(0);
					weight.setText(HealthGem.getSharedPreferences()
							.loadPreferences(SPreference.REGISTER_WEIGHTPOUNDS));
				} else {
					weightUnit.setSelection(1);
					Double kg = Double.parseDouble(HealthGem
							.getSharedPreferences().loadPreferences(
									SPreference.REGISTER_WEIGHTPOUNDS)) / 2.2;
					weight.setText(kg + "");
				}

				if (settingsDao.isHeightSettingInFeet()) {
					registrationHeightInputFeetInches
							.setVisibility(View.VISIBLE);
					heightUnit.setSelection(0);
					double feet = Double.parseDouble(HealthGem
							.getSharedPreferences().loadPreferences(
									SPreference.REGISTER_HEIGHTINCHES)) / 12;
					double inches = Double.parseDouble(HealthGem
							.getSharedPreferences().loadPreferences(
									SPreference.REGISTER_HEIGHTINCHES)) % 12;
					height.setText(feet + "");
					heightInches.setText(inches + "");
				}

				else {
					registrationHeightInputFeetInches.setVisibility(View.GONE);
					heightUnit.setSelection(1);
					Double cm = Double.parseDouble(HealthGem
							.getSharedPreferences().loadPreferences(
									SPreference.REGISTER_HEIGHTINCHES)) / 0.39370;
					height.setText(cm + "");
				}

				Resources res = getResources();
				String[] genderArray = res.getStringArray(R.array.gender);

				if (HealthGem.getSharedPreferences()
						.loadPreferences(SPreference.REGISTER_GENDER)
						.equals(genderArray[0]))
					gender.setSelection(0);
				else
					gender.setSelection(1);
			}
		}
		showDate(year, month + 1, day);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == 999)
			return new DatePickerDialog(this, myDateListener, year, month, day);

		return null;
	}

	private final DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker arg0, int year, int month, int day) {
			showDate(year, month + 1, day);
		}
	};

	private void showDate(int year, int month, int day) {
		yyyy = String.valueOf(year);
		mm = String.valueOf(month);
		dd = String.valueOf(day);

		if (month < 10)
			mm = "0" + mm;
		if (day < 10)
			dd = "0" + dd;
		birthdate.setText(new StringBuilder().append(yyyy).append("-")
				.append(mm).append("-").append(dd));
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

				if (heightUnit.getSelectedItemPosition() == 0) {
					double inches = Double.parseDouble(height.getText()
							.toString())
							* 12
							+ Double.parseDouble(heightInches.getText()
									.toString());
					HealthGem.getSharedPreferences().savePreferences(
							SPreference.REGISTER_HEIGHTINCHES, inches + "");
					settingsDao.setHeightToFeet();
				} else {
					Double ft = Double.parseDouble(height.getText().toString()) * 0.39370;
					HealthGem.getSharedPreferences().savePreferences(
							SPreference.REGISTER_HEIGHTINCHES, ft + "");
					settingsDao.setHeightToCentimeters();
				}

				if (weightUnit.getSelectedItemPosition() == 0) {
					HealthGem.getSharedPreferences().savePreferences(
							SPreference.REGISTER_WEIGHTPOUNDS,
							weight.getText().toString());
					settingsDao.setWeightToPounds();
				} else {
					Double pounds = Double.parseDouble(weight.getText()
							.toString()) * 2.2;
					HealthGem.getSharedPreferences().savePreferences(
							SPreference.REGISTER_WEIGHTPOUNDS, pounds + "");
					settingsDao.setWeightToKilograms();
				}

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

				if (heightUnit.getSelectedItemPosition() == 0) {
					double inches = Double.parseDouble(height.getText()
							.toString())
							* 12
							+ Double.parseDouble(heightInches.getText()
									.toString());
					editedUser.setHeight(inches);
					settingsDao.setHeightToFeet();
				} else {
					Double ft = Double.parseDouble(height.getText().toString()) * 0.39370;
					editedUser.setHeight(ft);
					settingsDao.setHeightToCentimeters();
				}

				if (weightUnit.getSelectedItemPosition() == 0) {
					editedUser.setWeight(Double.parseDouble(weight.getText()
							.toString()));
					settingsDao.setWeightToPounds();
				} else {
					Double pounds = Double.parseDouble(weight.getText()
							.toString()) * 2.2;
					editedUser.setWeight(pounds);
					settingsDao.setWeightToKilograms();
				}

				Date date = new Date();
				Timestamp timestamp = new Timestamp(date.getTime());
				Weight weightEntry = new Weight(timestamp, "", null,
						editedUser.getWeight());
				try {
					weightService.add(weightEntry);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					Toast.makeText(HealthGem.getContext(),
							"No Internet Connection !", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				} catch (OutdatedAccessTokenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/*
				 * User currUser = userService.getUser(); editedUser
				 * .setPhoto(new PHRImage(ImageHandler
				 * .encodeImageToBase64(ImageHandler
				 * .loadImage(currUser.getPhoto() .getFileName())),
				 * PHRImageType.IMAGE));
				 */
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

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.putExtra("backToMenu", 4);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
