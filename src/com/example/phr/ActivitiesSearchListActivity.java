package com.example.phr;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.service.ActivityService;
import com.example.phr.serviceimpl.ActivityServiceImpl;

public class ActivitiesSearchListActivity extends android.app.Activity {
	EditText searchWord;
	ListView searchList;
	ImageButton searchButton;
	ArrayAdapter<String> adapter;
	ArrayList<Activity> result;
	ArrayList<String> resultName;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		setContentView(R.layout.activity_search_list);
		setTitle("Activity Search List");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		searchList = (ListView) findViewById(R.id.searchList);
		searchWord = (EditText) findViewById(R.id.searchWord);
		searchButton = (ImageButton) findViewById(R.id.searchButton);

		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				/*
				 * result = new ArrayList<ActivitySingle>(); result.add(new
				 * ActivitySingle(1, "jogging", 150.5)); result.add(new
				 * ActivitySingle(2, "running", 200.5));
				 */
				ActivityService service = new ActivityServiceImpl();
				Log.e("search word", searchWord.getText().toString());
				result = new ArrayList<Activity>();
				try {
					result = (ArrayList<Activity>) service.search(searchWord
							.getText().toString());
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OutdatedAccessTokenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resultName = new ArrayList<String>();
				if (result != null) {
					for (int i = 0; i < result.size(); i++)
						resultName.add(result.get(i).getName());

					adapter = new ArrayAdapter<String>(getApplicationContext(),
							R.layout.item_custom_listview, resultName);

					searchList.setAdapter(adapter);
				}
			}
		});

		searchList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// int current = resultName.get(arg2);
				Activity chosenItem = result.get(arg2);
				Intent intent = new Intent();
				intent.putExtra("activity chosen", chosenItem);
				Log.e("activity chosen", chosenItem.getName());
				setResult(3, intent);
				finish();
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_add_new_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_add_new_activity:
			Intent i = new Intent(getApplicationContext(),
					AddNewActivityActivity.class);
			startActivity(i);

		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
