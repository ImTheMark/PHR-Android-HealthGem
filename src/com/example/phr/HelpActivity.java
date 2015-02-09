package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.phr.application.HealthGem;
import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.local_db.SPreference;

public class HelpActivity extends Activity {
	
	private ActionBar actionBar;
	private SliderLayout mDemoSlider;
	private List<Integer> helpList;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		mDemoSlider = (SliderLayout)findViewById(R.id.help_slide);
		
		setTitle("Tutorial");
		
		helpList = new ArrayList<Integer>();
		helpList.add(R.drawable.instruction1);
		helpList.add(R.drawable.instruction2);
		helpList.add(R.drawable.instruction3);
		helpList.add(R.drawable.instruction4);
		helpList.add(R.drawable.instruction5);
		helpList.add(R.drawable.instruction6);
		helpList.add(R.drawable.instruction7);
		helpList.add(R.drawable.instruction8);
		helpList.add(R.drawable.instruction9);
		helpList.add(R.drawable.instruction10);
		
		for(Integer image : helpList) {
			DefaultSliderView textSliderView = new DefaultSliderView(this);
			textSliderView
			//.description(name)
			.image(image)
			.setScaleType(BaseSliderView.ScaleType.Fit);
			//.setOnSliderClickListener(this);

			textSliderView.getBundle();
			//.putString("extra",name);

			mDemoSlider.addSlider(textSliderView);
		}

		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
		mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mDemoSlider.setCustomAnimation(new DescriptionAnimation());
		mDemoSlider.setDuration(4000);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case R.id.menu_done: {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			}
		    
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_tutorial, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(),
				MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	

}
