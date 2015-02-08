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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phr.adapter.CheckupAdapter;
import com.example.phr.application.HealthGem;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.serviceimpl.CheckUpTrackerServiceImpl;

public class CheckupTrackerActivity extends Activity {

	ListView mCheckupList;
	CheckupAdapter checkupAdapter;
	LinearLayout mBtnCheckupPost;;
	LinearLayout mBtnCheckupDoctor;
	List<CheckUp> list;
	CheckUpTrackerServiceImpl checkupServiceImpl;
	CheckUp chosenItem;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_checkup_tracker);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#030203")));
		setTitle("Checkup Tracker");
		mCheckupList = (ListView) findViewById(R.id.listView_checkup);

		list = new ArrayList<CheckUp>();
		checkupServiceImpl = new CheckUpTrackerServiceImpl();
		try {

			list = checkupServiceImpl.getAll();

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		Log.e(String.valueOf(list.size()), "size");
		checkupAdapter = new CheckupAdapter(getApplicationContext(), list);
		mCheckupList.setAdapter(checkupAdapter);
		mCheckupList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(getApplicationContext(),
						CheckupTrackerReadModeActivity.class);
				chosenItem = (CheckUp) arg0.getAdapter().getItem(arg2);
				i.putExtra("object", chosenItem);
				startActivity(i);
			}
		});

		mBtnCheckupPost = (LinearLayout) findViewById(R.id.btnAddCheckupDate);
		mBtnCheckupPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.CHECKUP);
				startActivity(i);
			}
		});

		mBtnCheckupDoctor = (LinearLayout) findViewById(R.id.btnAddCheckupDoctor);
		mBtnCheckupDoctor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.CHECKUP);
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activitycheckup_menu_tracker_help, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void displayhelp() {

		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.item_help);
		ImageView image = (ImageView) dialog.findViewById(R.id.help_imageview);
		image.setBackgroundResource(R.drawable.checkuptracker_help);
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
