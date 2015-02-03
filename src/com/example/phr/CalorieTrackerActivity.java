package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phr.adapter.CalorieAdapter;
import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.CalorieTrackerEntry;
import com.example.phr.service.CalorieTrackerService;
import com.example.phr.serviceimpl.CalorieTrackerEntryServiceImpl;

public class CalorieTrackerActivity extends Activity {

	ListView mCalorieList;
	CalorieAdapter calorieAdapter;
	List<CalorieTrackerEntry> calorieTrackerList;
	CalorieTrackerEntry chosenItem;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_calorie_tracker);
		setTitle("Calorie Tracker");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A3A47")));
		mCalorieList = (ListView) findViewById(R.id.listview_calorie);

		calorieTrackerList = new ArrayList<CalorieTrackerEntry>();

		CalorieTrackerService calService = new CalorieTrackerEntryServiceImpl();

		try {
			calorieTrackerList = calService.getAll();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		calorieAdapter = new CalorieAdapter(getApplicationContext(),
				calorieTrackerList);
		mCalorieList.setAdapter(calorieAdapter);
		mCalorieList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("calorie", "CLICKED!");
				Intent i = new Intent(getApplicationContext(),
						CalorieBreakdownTrackerActivity.class);
				chosenItem = (CalorieTrackerEntry) arg0.getAdapter().getItem(
						arg2);
				i.putExtra("object", chosenItem);
				startActivity(i);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_tracker_help, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void displayhelp() {

		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.item_help);
		ImageView image = (ImageView) dialog.findViewById(R.id.help_imageview);
		image.setBackgroundResource(R.drawable.calorietracker_help);
		dialog.getWindow().setBackgroundDrawable(null);
		dialog.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.action_help:
			displayhelp();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

}
