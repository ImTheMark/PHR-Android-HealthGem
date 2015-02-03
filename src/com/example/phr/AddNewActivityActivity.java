package com.example.phr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;

public class AddNewActivityActivity extends android.app.Activity {

	EditText newActivity;
	EditText newActivityCal;
	EditText newActivityDuration;
	Spinner newActivityDurationUnit;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setTitle("Add New Activity");
		setContentView(R.layout.activity_add_new_activity);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A3A47")));

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

			Double calBurnedHr = 0.0;
			if (newActivityDurationUnit.getSelectedItem().equals("min"))
				calBurnedHr = (Double.parseDouble(newActivityCal.getText()
						.toString()) / Double.parseDouble(newActivityDuration
						.getText().toString())) * 60;
			else
				calBurnedHr = Double.parseDouble(newActivityCal.getText()
						.toString())
						/ Double.parseDouble(newActivityDuration.getText()
								.toString());
			WeightTrackerService weightService = new WeightTrackerServiceImpl();
			Weight weight = null;
			try {
				weight = weightService.getLatest();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Double computedMet = calBurnedHr / weight.getWeightInKilograms();

			Activity addActivity = new Activity(newActivity.getText()
					.toString(), computedMet);
			Log.e("new", addActivity.getName());
			i.putExtra("from", "new activity");
			i.putExtra("activity added", addActivity);
			i.putExtra("activity_duration", Double
					.parseDouble(newActivityDuration.getText().toString()));
			i.putExtra("activity_unit",
					String.valueOf(newActivityDurationUnit.getSelectedItem()));

			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
