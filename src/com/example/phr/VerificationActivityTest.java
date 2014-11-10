package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phr.adapter.UnverifiedStatusAdapter;
import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.service.VerificationService;
import com.example.phr.serviceimpl.VerificationServiceImpl;

@SuppressLint("NewApi")
public class VerificationActivityTest extends Activity {

	SwipeRefreshLayout swipeLayout;
	ListView listView;
	VerificationService vService;
	UnverifiedStatusAdapter adapter;
	List<TrackerEntry> unverifiedList;
	VerificationService verificationService;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		verificationService = new VerificationServiceImpl();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_verification_test);
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new GetPostsFromServer().execute();
			}
		});
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		listView = (ListView) findViewById(R.id.verification_listview_test);
		vService = new VerificationServiceImpl();
		try {
			unverifiedList = vService.getAll();
			verificationService.setUnverifiedPostsCount(unverifiedList.size());
			Log.e("size ", unverifiedList.size() + "");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(HealthGem.getContext(),
							"Hello, Pls connect to the wifi!",
							Toast.LENGTH_SHORT).show();
				}
			});
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter = new UnverifiedStatusAdapter(HealthGem.getContext(),
				unverifiedList);
		listView.setAdapter(adapter);
	}

	class GetPostsFromServer extends AsyncTask<Void, Void, Void> {

		private List<TrackerEntry> list;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			list = new ArrayList<TrackerEntry>();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				vService.updateListOfUnverifiedPosts();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OutdatedAccessTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			try {
				list = vService.getAll();

				if (list != null && !list.isEmpty()) {
					unverifiedList = list;
					verificationService.setUnverifiedPostsCount(list.size());
					Log.e("VERIFICATION size after post execute",
							unverifiedList.size() + "");
					// adapter.notifyDataSetChanged();
					/*
					 * ((UnverifiedStatusAdapter) listView.getAdapter())
					 * .notifyDataSetChanged();
					 */
					Intent intent = getIntent();
					finish();
					startActivity(intent);
				}

			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OutdatedAccessTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			swipeLayout.setRefreshing(false);
		}
	}

}
