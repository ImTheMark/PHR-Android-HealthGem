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
import android.widget.Toast;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_user_information);
		
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
		
		fullName.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_NAME));
		contactNumber.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_CONTACTNUMBER));
		email.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_EMAIL));
		height.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_HEIGHT));
		weight.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_WEIGHT));
		contactPerson.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_CONTACTPERSON));
		contactPersonNumber.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_CONTACTPERSONNUMBER));
		allergies.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_ALLERGIES));
		knownHealthProblems.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_KNOWNHEALTHPROBLEMS));
		birthdate.setText(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_BIRTHDATE));
		gender.setSelection(Integer.parseInt(HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_GENDER)));
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
			if(fullName.getText().toString().equals("") ||
					contactNumber.getText().toString().equals("") ||
					email.getText().toString().equals("") ||
					height.getText().toString().equals("") ||
					weight.getText().toString().equals("") ||
					contactPerson.getText().toString().equals("") ||
					contactPersonNumber.getText().toString().equals("") ||
					birthdate.getText().toString().equals("")){
				Toast.makeText(HealthGem.getContext(), "Please complete the missing fields.", Toast.LENGTH_LONG).show();	
			}
			else{
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_NAME, fullName.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_CONTACTNUMBER, contactNumber.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_EMAIL, email.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_HEIGHT, height.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_WEIGHT, weight.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_CONTACTPERSON, contactPerson.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_CONTACTPERSONNUMBER, contactPersonNumber.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_ALLERGIES, allergies.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_KNOWNHEALTHPROBLEMS, knownHealthProblems.getText().toString());
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_GENDER, gender.getSelectedItemPosition()+"");
				HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_BIRTHDATE, birthdate.getText().toString()+" 00:00:00");
				
				HealthGem.getSharedPreferences().savePreferences(SPreference.SETTINGS_HEIGHTUNIT, heightUnit.getSelectedItemPosition()+"");
				HealthGem.getSharedPreferences().savePreferences(SPreference.SETTINGS_WEIGHTUNIT, weightUnit.getSelectedItemPosition()+"");
				
				
				Intent intent = new Intent(getApplicationContext(), RegisterFBLoginActivity.class);
				startActivity(intent);
			}			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
