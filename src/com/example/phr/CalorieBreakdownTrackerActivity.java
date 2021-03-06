package com.example.phr;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.phr.adapter.CalorieBreakdownAdapter;
import com.example.phr.mobile.models.CalorieTrackerEntry;
import com.example.phr.mobile.models.TrackerEntry;

@SuppressLint("NewApi")
public class CalorieBreakdownTrackerActivity extends Activity {

	ListView mCalorieList;
	CalorieBreakdownAdapter calorieAdapter;
	ImageView mBtnBloodsugarPost;
	List<TrackerEntry> list;
	CalorieTrackerEntry chosenItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_calorie_breakdown_tracker);
		setTitle("Calorie Breakdown Tracker");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#030203")));
		mCalorieList = (ListView) findViewById(R.id.breakdown_list);

		Intent in = getIntent();
		chosenItem = (CalorieTrackerEntry) in.getExtras().getSerializable(
				"object");
		list = chosenItem.getList();
		calorieAdapter = new CalorieBreakdownAdapter(getApplicationContext(),
				list);

		mCalorieList.setAdapter(calorieAdapter);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return true;
	}

}
