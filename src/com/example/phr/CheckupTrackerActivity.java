package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import com.example.phr.adapter.CheckupAdapter;
import com.example.phr.model.Checkup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CheckupTrackerActivity extends Activity {

	ListView mCheckupList;
	CheckupAdapter checkupAdapter;
	LinearLayout mBtnCheckupPost;;
	LinearLayout mBtnCheckupDoctor;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkup_tracker);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("Checkup Tracker");
		mCheckupList = (ListView) findViewById(R.id.listView_checkup);
				
		// FAKE DATA
		List<Checkup> list = new ArrayList<Checkup>();
		
		Checkup data1 = new Checkup("Cold and Cough Checkup","Dr. Maria Rosario","The Angel's Clinic Place","23","July", null);
		
		Checkup data2 = new Checkup("General Checkup","Dr. Michael Lee","UST hospital","4","May", null);
		
		Checkup data3 = new Checkup("Braces","Dra. Maria Anton","Ortigas","5","Aug", null);

		Checkup data4 = new Checkup("Nutrition check","Dr. Michael Lee","UST hospital","15","Mar",null);

		list.add(data1);
		list.add(data2);
		list.add(data3);
		list.add(data4);
		

		checkupAdapter = new CheckupAdapter(getApplicationContext(), list);
		mCheckupList.setAdapter(checkupAdapter);
		mCheckupList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("checkup", "CLICKED!");
			}
		});
		
		mBtnCheckupPost = (LinearLayout) findViewById(R.id.btnAddCheckupDate);
		mBtnCheckupPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {/*
				Intent intent = new Intent(getApplicationContext(),
						CheckupPostActivity.class);
				startActivity(intent);*/
			}
		});
		
		mBtnCheckupDoctor = (LinearLayout) findViewById(R.id.btnAddCheckupDoctor);
		mBtnCheckupDoctor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {/*
				Intent intent = new Intent(getApplicationContext(),
						CheckupTrackerAddDoctorActivity.class);
				startActivity(intent);*/
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
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        case android.R.id.home: 
            onBackPressed();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
