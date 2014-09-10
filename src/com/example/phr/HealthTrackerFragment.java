package com.example.phr;

import com.example.phr.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class HealthTrackerFragment extends Fragment {

	private LinearLayout mBtnFoodTracker;
	private LinearLayout mBtnCheckupTracker;
	private LinearLayout mBtnBloodPressureTracker;
	private LinearLayout mBtnBloodSugarTracker;
	private LinearLayout mBtnNoteTracker;
	private LinearLayout mBtnActivityTracker;
	private LinearLayout mBtnWeightTracker;
	private LinearLayout mBtnCalorieTracker;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_health_tracker, container,
				false);
		
		mBtnFoodTracker = (LinearLayout)rootView.findViewById(R.id.btnFoodTracker);
		mBtnFoodTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						GroupedFoodTrackerActivity.class);
				startActivity(intent);
			}
		});
		
		mBtnCheckupTracker = (LinearLayout)rootView.findViewById(R.id.btnCheckupTracker);
		mBtnCheckupTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						CheckupTrackerActivity.class);
				startActivity(intent);
			}
		});
		
		
		mBtnBloodPressureTracker = (LinearLayout)rootView.findViewById(R.id.btnBloodPressureTracker);
		mBtnBloodPressureTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						BloodPressureTrackerActivity.class);
				startActivity(intent);
			}
		});
		
		mBtnBloodSugarTracker = (LinearLayout)rootView.findViewById(R.id.btnBloodSugarTracker);
		mBtnBloodSugarTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						BloodSugarTrackerActivity.class);
				startActivity(intent);
			}
		});
		
		mBtnNoteTracker = (LinearLayout)rootView.findViewById(R.id.btnNotesTracker);
		mBtnNoteTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						NoteTrackerActivity.class);
				startActivity(intent);
			}
		});
		
	
		mBtnWeightTracker = (LinearLayout)rootView.findViewById(R.id.btnWeightTracker);
		mBtnWeightTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						WeightTrackerActivity.class);
				startActivity(intent);
			}
		});
		
		mBtnCalorieTracker = (LinearLayout)rootView.findViewById(R.id.btnCalorieTracker);
		mBtnCalorieTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						CalorieTrackerActivity.class);
				startActivity(intent);
			}
		});
		
		
		mBtnActivityTracker = (LinearLayout)rootView.findViewById(R.id.btnActivitiesTracker);
		mBtnActivityTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						ActivitiesTrackerActivity.class);
				startActivity(intent);
			}
		});
		
		return rootView;
	}

}
