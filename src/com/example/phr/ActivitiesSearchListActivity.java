package com.example.phr;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.example.phr.application.HealthGem;
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
	private android.app.Activity activity;

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
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#030203")));
		searchList = (ListView) findViewById(R.id.searchList);
		searchWord = (EditText) findViewById(R.id.searchWord);
		searchButton = (ImageButton) findViewById(R.id.searchButton);
		activity = this;

		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				final ActivityService service = new ActivityServiceImpl();
				Log.e("search word", searchWord.getText().toString());
				result = new ArrayList<Activity>();
				resultName = new ArrayList<String>();

				final ProgressDialog progressDialog = new ProgressDialog(
						activity);
				progressDialog.setCancelable(false);
				progressDialog.setMessage("Searching activity...");
				progressDialog.show();

				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						try {
							result = (ArrayList<Activity>) service
									.search(searchWord.getText().toString());
						} catch (ServiceException e) {
							// TODO Auto-generated catch block

							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(HealthGem.getContext(),
											"No Internet Connection!",
											Toast.LENGTH_LONG).show();
								}
							});
							e.printStackTrace();
						} catch (OutdatedAccessTokenException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result2) {
						if (result != null) {
							for (int i = 0; i < result.size(); i++)
								resultName.add(result.get(i).getName());

							adapter = new ArrayAdapter<String>(
									getApplicationContext(),
									R.layout.item_custom_listview, resultName);

							searchList.setAdapter(adapter);
						}

						if (progressDialog.isShowing())
							progressDialog.dismiss();
					};
				}.execute();
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

		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.menu_item_add_new_activity:
			Intent i = new Intent(getApplicationContext(),
					AddNewActivityActivity.class);
			startActivity(i);

		default:
			return super.onOptionsItemSelected(item);
		}
		return true;

	}

}
