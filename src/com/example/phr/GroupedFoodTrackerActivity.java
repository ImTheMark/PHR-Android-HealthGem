package com.example.phr;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.phr.adapter.GroupedFoodAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.dao.MobileFoodTrackerDao;
import com.example.phr.mobile.daoimpl.MobileFoodTrackerDaoImpl;
import com.example.phr.mobile.models.GroupedFood;

public class GroupedFoodTrackerActivity extends Activity {

	ListView mGroupedFoodList;
	GroupedFoodAdapter groupedfoodAdapter;
	ImageView mBtnGroupedFoodPost;
	Timestamp timestamp;
	GroupedFood groupedFood;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_groupedfood_tracker);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("Food Tracker");
		mGroupedFoodList = (ListView) findViewById(R.id.listView_groupedfood);
		List<GroupedFood> list = new ArrayList<GroupedFood>();
		// FAKE DATA
		/*
		 * List<GroupedFood> list = new ArrayList<GroupedFood>(); GroupedFood
		 * data1 = new GroupedFood(null, 459, 20.41, 24.89, 43.07);
		 * 
		 * GroupedFood data2 = new GroupedFood(null, 679, 30.3, 18, 50);
		 * GroupedFood data3 = new GroupedFood(null, 152, 883, 23, 344);
		 * 
		 * GroupedFood data4 = new GroupedFood(null, 1100, 30.3, 18, 50);
		 * GroupedFood data5 = new GroupedFood(null, 400, 883, 23, 344);
		 * GroupedFood data6 = new GroupedFood(null, 598, 30.3, 18, 50);
		 * GroupedFood data7 = new GroupedFood(null, 152, 883, 23, 344);
		 * 
		 * list.add(data1); list.add(data2); list.add(data3); list.add(data4);
		 * list.add(data5); list.add(data6); list.add(data7);
		 */

		MobileFoodTrackerDao foodTrackerDao = new MobileFoodTrackerDaoImpl();
		try {
			list = foodTrackerDao.getAllGroupedByDateCalculated();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		groupedfoodAdapter = new GroupedFoodAdapter(getApplicationContext(),
				list);
		mGroupedFoodList.setAdapter(groupedfoodAdapter);
		mGroupedFoodList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("groupedfood", "CLICKED!");
				Intent i = new Intent(getApplicationContext(),
						FoodTrackerDailyActivity.class);
				groupedFood = (GroupedFood) arg0.getAdapter().getItem(arg2);
				SimpleDateFormat fmt = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				String txtdate = fmt.format(groupedFood.getDate());
				Log.e("groupedFoodtracker", txtdate);
				i.putExtra("date", txtdate);
				startActivity(i);
			}
		});

		mBtnGroupedFoodPost = (ImageView) findViewById(R.id.foodBanner_post);
		mBtnGroupedFoodPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						NewStatusActivity.class);
				intent.putExtra("tracker", TrackerInputType.FOOD);
				startActivity(intent);
			}
		});
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
		image.setBackgroundResource(R.drawable.foodtracker_help);
		dialog.getWindow().setBackgroundDrawable(null);
		dialog.show();
	}

}
