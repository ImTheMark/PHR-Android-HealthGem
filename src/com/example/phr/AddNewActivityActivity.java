package com.example.phr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.phr.mobile.models.Activity;

public class AddNewActivityActivity extends android.app.Activity {

	EditText newActivity;
	EditText newActivityCal;
	EditText newActivityDuration;
	Spinner newActivityDurationUnit;

	@Override
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

			Intent i = new Intent(getApplicationContext(),
					NewStatusActivity.class);
			Double computedMet = 100.0;
			Activity addActivity = new Activity(newActivity.getText()
					.toString(), computedMet);
			Log.e("new", addActivity.getName());
			i.putExtra("from", "new activity");
			i.putExtra("activity added", addActivity);
			i.putExtra("activity_duration", newActivityDuration.getText()
					.toString());
			i.putExtra("activity_unit",
					String.valueOf(newActivityDurationUnit.getSelectedItem()));
			/*
			 * i.putExtra("activity_name",newActivity.getText().toString());
			 * i.putExtra("activity_cal",newActivityCal.getText().toString());
			 * i.
			 * putExtra("activity_duration",newActivityDuration.getText().toString
			 * ());
			 * i.putExtra("activity_unit",String.valueOf(newActivityDurationUnit
			 * .getSelectedItem()));
			 */
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
