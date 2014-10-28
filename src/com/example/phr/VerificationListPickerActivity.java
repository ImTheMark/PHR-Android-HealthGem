package com.example.phr;

import java.sql.Timestamp;
import java.util.List;

import com.example.phr.adapter.ActivityAdapter;
import com.example.phr.adapter.SingleFoodAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.service.VerificationService;
import com.example.phr.serviceimpl.VerificationServiceImpl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VerificationListPickerActivity extends android.app.Activity {
	
	ListView listView;
	UnverifiedRestaurantEntry restaurantEntry;
	UnverifiedSportsEstablishmentEntry sportsEntry;
	VerificationService verificationService;
	List<Food> foodList;
	List<Activity> activityList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_actions);
		listView = (ListView) findViewById(R.id.actionList);
		verificationService = new VerificationServiceImpl();
		
		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();
		
		if (extras != null && in.hasExtra("restaurant")) {
			restaurantEntry = (UnverifiedRestaurantEntry) in.getExtras().getSerializable("restaurant");
			
			foodList = verificationService.getFoodList();
			
			SingleFoodAdapter adapter = new SingleFoodAdapter(getApplicationContext(), foodList);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					Food chosenFood = foodList.get(arg2);
					
					FoodTrackerEntry foodEntry = new FoodTrackerEntry(
							restaurantEntry.getEntryID(), 
							restaurantEntry.getFacebookID(),
							restaurantEntry.getTimestamp(), 
							restaurantEntry.getStatus(), 
							restaurantEntry.getImage(), 
							chosenFood,
							1.0);
					
					Intent i = new Intent(getApplicationContext(), NewStatusActivity.class);
					i.putExtra("unverified", TrackerInputType.FOOD);
					i.putExtra("object", restaurantEntry);
					i.putExtra("object1", foodEntry);
					startActivity(i);
				}
			});
		}
		else if (extras != null && in.hasExtra("sportestablishment")) {
			sportsEntry = (UnverifiedSportsEstablishmentEntry) in.getExtras().getSerializable("sportestablishment");
			
			activityList = verificationService.getActivityList();
			
			//ActivityAdapter adapter = new ActivityAdapter(getApplicationContext(), activityList);
			//listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					Activity chosenActivity = activityList.get(arg2);
					
					ActivityTrackerEntry actEntry = new ActivityTrackerEntry(
							sportsEntry.getEntryID(), 
							sportsEntry.getFacebookID(),
							sportsEntry.getTimestamp(), 
							sportsEntry.getStatus(), 
							sportsEntry.getImage(),
							chosenActivity, 
							1.0,
							3600);
					
					Intent i = new Intent(getApplicationContext(), NewStatusActivity.class);
					i.putExtra("unverified", TrackerInputType.ACTIVITY);
					i.putExtra("object", sportsEntry);
					i.putExtra("object1", actEntry);
					startActivity(i);
				}
			});
		}
	}

}
