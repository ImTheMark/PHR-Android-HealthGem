package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phr.adapter.SingleFoodAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.VerificationService;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.VerificationServiceImpl;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;

public class VerificationListPickerActivity extends android.app.Activity {

	ListView listView;
	UnverifiedRestaurantEntry restaurantEntry;
	UnverifiedSportsEstablishmentEntry sportsEntry;
	VerificationService verificationService;
	List<Food> foodList;
	List<Activity> activityList;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_add_actions);
		listView = (ListView) findViewById(R.id.actionList);
		verificationService = new VerificationServiceImpl();

		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();

		if (extras != null && in.hasExtra("restaurant")) {
			restaurantEntry = (UnverifiedRestaurantEntry) in.getExtras()
					.getSerializable("restaurant");
			foodList = verificationService
					.getFoodListGivenRestaurantID(restaurantEntry
							.getRestaurant().getEntryID());
			Log.e("verification", foodList.size() + "");

			SingleFoodAdapter adapter = new SingleFoodAdapter(
					getApplicationContext(), foodList);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					Food chosenFood = foodList.get(arg2);

					FoodTrackerEntry foodEntry = new FoodTrackerEntry(
							restaurantEntry.getEntryID(), restaurantEntry
									.getFacebookID(), restaurantEntry
									.getTimestamp(), restaurantEntry
									.getStatus(), restaurantEntry.getImage(),
							chosenFood, 1.0);

					Intent i = new Intent(getApplicationContext(),
							NewStatusActivity.class);
					i.putExtra("unverified", TrackerInputType.RESTAURANT);
					i.putExtra("object", restaurantEntry);
					i.putExtra("object1", foodEntry);
					startActivity(i);
				}
			});
		} else if (extras != null && in.hasExtra("sportestablishment")) {
			sportsEntry = (UnverifiedSportsEstablishmentEntry) in.getExtras()
					.getSerializable("sportestablishment");
			new GetSportEstablishmentPostFromServer().execute();
		}
	}

	class GetSportEstablishmentPostFromServer extends
			AsyncTask<Void, Void, Void> {
		UnverifiedSportsEstablishmentEntry entry;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			Log.e("VERIFICATION ASYNC", "async start");
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {

				Log.e("VERIFICATION ASYNC", "pulling from web");

				entry = verificationService
						.getUnverifiedSportsEstablishmentPostFromWebDB(sportsEntry);

			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (OutdatedAccessTokenException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (EntryNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			Log.e("VERIFICATION ASYNC", "done");

			List<String> actListNameOnly = new ArrayList<String>();

			activityList = entry.getActivities();

			for (Activity act : activityList)
				actListNameOnly.add(act.getName());

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.item_custom_listview,
					actListNameOnly);

			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					Activity chosenActivity = activityList.get(arg2);
					Weight weight = null;
					WeightTrackerService weightService = new WeightTrackerServiceImpl();
					try {
						weight = weightService.getLatest();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double cal = chosenActivity.getMET()
							* weight.getWeightInKilograms() * 1;

					ActivityTrackerEntry actEntry = new ActivityTrackerEntry(
							sportsEntry.getEntryID(), sportsEntry
									.getFacebookID(), sportsEntry
									.getTimestamp(), sportsEntry.getStatus(),
							sportsEntry.getImage(), chosenActivity, cal, 3600);

					Intent i = new Intent(getApplicationContext(),
							NewStatusActivity.class);
					i.putExtra("unverified", TrackerInputType.SPORTS);
					i.putExtra("object", sportsEntry);
					i.putExtra("object1", actEntry);
					startActivity(i);
				}
			});
		}
	}

}
