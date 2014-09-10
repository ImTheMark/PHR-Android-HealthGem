package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.phr.adapter.CalorieAdapter;
import com.example.phr.adapter.FoodActivityCalorieAdapter;
import com.example.phr.model.Calorie;
import com.example.phr.model.FoodActivityCalorie;

public class CalorieBreakdownTrackerActivity extends Activity{

	
	ListView mCalorieList;
	FoodActivityCalorieAdapter calorieAdapter;
	ImageView mBtnBloodsugarPost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calorie_breakdown_tracker);
		setTitle("Calorie Breakdown Tracker");
		mCalorieList = (ListView) findViewById(R.id.breakdown_list);
				
		// FAKE DATA
		List<FoodActivityCalorie> list = new ArrayList<FoodActivityCalorie>();
		FoodActivityCalorie data1 = new FoodActivityCalorie(getResources().getDrawable(R.drawable.icon_food),"+ 222","1 cup","7:30 pm","Sinigang");

		FoodActivityCalorie data2 = new FoodActivityCalorie(getResources().getDrawable(R.drawable.icon_food),"+ 27","1 slice","7:30 pm","Bacon");
		
		FoodActivityCalorie data3 = new FoodActivityCalorie(getResources().getDrawable(R.drawable.icon_food),"+ 210","1 serving","7:30 pm","Hash brown");

		FoodActivityCalorie data4 = new FoodActivityCalorie(getResources().getDrawable(R.drawable.icon_activity),"- 246","30 mins","4:55 pm","Stationary cycling");
		
		list.add(data4);
		list.add(data1);
		list.add(data2);
		list.add(data3);
		
		
		

		calorieAdapter = new FoodActivityCalorieAdapter(getApplicationContext(), list);

		mCalorieList.setAdapter(calorieAdapter);
		

	}

}
