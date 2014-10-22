package com.example.phr;

import com.example.phr.application.HealthGem;
import com.example.phr.local_db.SPreference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class RegisterUserInformationActivity extends Activity {
	
	EditText lastName;
	EditText firstName;
	EditText middleName;
	EditText height;
	EditText weight;
	EditText contactPerson;
	EditText contactPersonNumber;
	EditText allergies;
	EditText knownHealthProblems;
	EditText birthdate;
	Spinner gender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_user_information);
		
		lastName = (EditText) findViewById(R.id.editTextRegistrationLastName);
		firstName = (EditText) findViewById(R.id.editTextRegistrationFirstName);
		middleName = (EditText) findViewById(R.id.editTextRegistrationMiddleName);
		height = (EditText) findViewById(R.id.txtRegistrationHeightInput);
		weight = (EditText) findViewById(R.id.txtRegistrationWeightInput);
		contactPerson = (EditText) findViewById(R.id.EditTextRegistrationEmergencyContactPerson);
		contactPersonNumber = (EditText) findViewById(R.id.EditTextRegistrationEmergencyContactNumber);
		allergies = (EditText) findViewById(R.id.EditTextRegistrationAllergies);
		knownHealthProblems = (EditText) findViewById(R.id.EditTextRegistrationHealthProblems);
		gender = (Spinner) findViewById(R.id.textViewRegistrationGender);
		birthdate = (EditText) findViewById(R.id.editTextRegistrationBirthDate);
		
		lastName.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_LASTNAME));
		firstName.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_FIRSTNAME));
		middleName.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_MIDDLENAME));
		height.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_HEIGHT));
		weight.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_WEIGHT));
		contactPerson.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_CONTACTPERSON));
		contactPersonNumber.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_CONTACTPERSONNUMBER));
		allergies.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_ALLERGIES));
		knownHealthProblems.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_KNOWNHEALTHPROBLEMS));
		birthdate.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_BIRTHDATE));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_registration_next, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_next:
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_LASTNAME, lastName.getText().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_FIRSTNAME, firstName.getText().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_MIDDLENAME, middleName.getText().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_HEIGHT, height.getText().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_WEIGHT, weight.getText().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_CONTACTPERSON, contactPerson.getText().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_CONTACTPERSONNUMBER, contactPersonNumber.getText().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_ALLERGIES, allergies.getText().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_KNOWNHEALTHPROBLEMS, knownHealthProblems.getText().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_GENDER, gender.getSelectedItem().toString());
			HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_BIRTHDATE, birthdate.getText().toString());
			Intent intent = new Intent(getApplicationContext(), RegisterFBLoginActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
