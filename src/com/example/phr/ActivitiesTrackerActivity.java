package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phr.adapter.ActivityAdapter;
import com.example.phr.application.HealthGem;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.serviceimpl.ActivityTrackerServiceImpl;

public class ActivitiesTrackerActivity extends Activity {

	ActivityAdapter activityAdapter;
	ListView mActivityList;
	ImageView mBtnAddActivity;
	List<ActivityTrackerEntry> activityList;
	ActivityTrackerServiceImpl activityServiceImpl;
	AlertDialog.Builder alertDialog;
	ArrayList<String> names;
	String mode;
	AlertDialog alertD;
	ActivityTrackerEntry chosenItem;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_activities_tracker);
		setTitle("Activity Tracker");
		mActivityList = (ListView) findViewById(R.id.listViewActivityTracker);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A3A47")));

		activityList = new ArrayList<ActivityTrackerEntry>();
		activityServiceImpl = new ActivityTrackerServiceImpl();
		try {
			activityList = activityServiceImpl.getAll();

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		Log.e(String.valueOf(activityList.size()), "size");

		activityAdapter = new ActivityAdapter(getApplicationContext(),
				activityList);
		mActivityList.setAdapter(activityAdapter);

		mActivityList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("activity", "CLICKED!");
				chosenItem = (ActivityTrackerEntry) arg0.getAdapter().getItem(
						arg2);
				mode = "";
				names = new ArrayList<String>();
				names.add("Edit");
				names.add("Delete");
				alertDialog = new AlertDialog.Builder(
						ActivitiesTrackerActivity.this);
				LayoutInflater inflater = getLayoutInflater();
				View convertView = inflater.inflate(R.layout.item_dialogbox,
						null);
				alertDialog.setView(convertView);
				alertDialog.setTitle("What to do?");
				ListView lv = (ListView) convertView
						.findViewById(R.id.dialogList);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_custom_listview,
						names);
				lv.setAdapter(adapter);

				lv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						mode = names.get(arg2);
						alertD.dismiss();
						Log.e("mode", names.get(arg2));

						if (mode.equals("Edit")) {

							Intent i = new Intent(getApplicationContext(),
									NewStatusActivity.class);
							i.putExtra("edit", TrackerInputType.ACTIVITY);
							i.putExtra("object", chosenItem);
							startActivity(i);
						} else if (mode.equals("Delete")) {

							try {
								Log.e("activity", "del");
								activityServiceImpl.delete(chosenItem);
								Log.e("activity", "del_done");
								Intent i = new Intent(getApplicationContext(),
										ActivitiesTrackerActivity.class);
								startActivity(i);
							} catch (ServiceException e) {
								// TODO Auto-generated catch block
								Toast.makeText(HealthGem.getContext(),
										"No Internet Connection !",
										Toast.LENGTH_LONG).show();
								e.printStackTrace();
							} catch (OutdatedAccessTokenException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (EntryNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}

				});
				alertD = alertDialog.create();
				alertD.show();
				Log.e("in", "in");

			}
		});

		mBtnAddActivity = (ImageView) findViewById(R.id.btnAddActivity);
		mBtnAddActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.ACTIVITY);
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
		image.setBackgroundResource(R.drawable.activitytracker_help);
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

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.putExtra("backToMenu", 2);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
