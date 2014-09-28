package com.example.phr;

import com.example.phr.enums.TrackerInputType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewActivityActivity extends Activity{

	EditText newActivity;
	EditText newActivityCal;
	EditText newActivityDuration;
	Spinner newActivityDurationUnit;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Add New Activity");
		setContentView(R.layout.activity_add_new_activity);
		
		newActivity = (EditText) findViewById(R.id.txtNewActivity);
		newActivityCal = (EditText) findViewById(R.id.txtNewCal);
		newActivityDuration = (EditText) findViewById(R.id.txtNewDuration);
		newActivityDurationUnit = (Spinner) findViewById(R.id.spinnerNewDurationUnit);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_add_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_activity:
			
			Intent i = new Intent(getApplicationContext(), NewStatusActivity.class);
			i.putExtra("from","new activity");
			i.putExtra("activity_name",newActivity.getText().toString());
			i.putExtra("activity_cal",newActivityCal.getText().toString());
			i.putExtra("activity_duration",newActivityDuration.getText().toString());
			i.putExtra("activity_unit",String.valueOf(newActivityDurationUnit
					.getSelectedItem()));
			startActivity(i);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	
}
