package com.example.phr;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.phr.adapter.CalorieBreakdownAdapter;
import com.example.phr.mobile.models.CalorieTrackerEntry;
import com.example.phr.mobile.models.TrackerEntry;

public class CalorieBreakdownTrackerActivity extends Activity {

	ListView mCalorieList;
	CalorieBreakdownAdapter calorieAdapter;
	ImageView mBtnBloodsugarPost;
	List<TrackerEntry> list;
	CalorieTrackerEntry chosenItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calorie_breakdown_tracker);
		setTitle("Calorie Breakdown Tracker");
		mCalorieList = (ListView) findViewById(R.id.breakdown_list);

		Intent in = getIntent();
		chosenItem = (CalorieTrackerEntry) in.getExtras().getSerializable(
				"object");
		list = chosenItem.getList();
		calorieAdapter = new CalorieBreakdownAdapter(getApplicationContext(),
				list);

		mCalorieList.setAdapter(calorieAdapter);

	}

}
