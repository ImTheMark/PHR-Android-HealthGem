package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.phr.adapter.StatusAdapter;
import com.example.phr.model.Status;

public class StatusFeedActivity extends ListActivity {

	StatusAdapter statusAdapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A3A47")));
		setContentView(R.layout.activity_status_feed);/*
													 * statusAdapter = new
													 * StatusAdapter
													 * (getApplicationContext(),
													 * generateData());
													 * setListAdapter
													 * (statusAdapter);
													 */

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private List<Status> generateData() {
		List<Status> list = new ArrayList<Status>();

		Status feeling = new Status();
		feeling.setActionHolder("Weighting");
		feeling.setActionName("180 lbs");
		feeling.setDatettime("Jul 12, 2014 9:40 pm");
		feeling.setStatus("Feeling good!");
		feeling.setActionImgUrl(getResources().getDrawable(
				R.drawable.icon_weight));
		feeling.setPostViaImgUrl(getResources().getDrawable(
				R.drawable.activitystatusfeed_icon_small_facebook));
		feeling.setStatusImgUrl(null);
		// feeling.setImage("");
		list.add(feeling);

		Status eat = new Status();
		eat.setActionHolder("Eating");
		eat.setActionName("Sinigang");
		eat.setDatettime("Jul 12, 2014 7:30 pm");
		eat.setStatus("YUM YUM YUM :)))");
		eat.setActionImgUrl(getResources().getDrawable(R.drawable.icon_food));
		eat.setPostViaImgUrl(getResources().getDrawable(
				R.drawable.activitystatusfeed_heart_gem_supersmall));
		eat.setStatusImgUrl(getResources()
				.getDrawable(R.drawable.food_sinigang));
		// eat.setImage("food_sinigang");
		list.add(eat);

		Status eat2 = new Status();
		eat2.setActionHolder("Eating");
		eat2.setActionName("Bacon");
		eat2.setDatettime("Jul 12, 2014 5:30 pm");
		eat2.setStatus("Bacon. Love bacon. #bacon");
		eat2.setActionImgUrl(getResources().getDrawable(R.drawable.icon_food));
		eat2.setPostViaImgUrl(getResources().getDrawable(
				R.drawable.activitystatusfeed_heart_gem_supersmall));
		eat2.setStatusImgUrl(null);
		// eat2.setImage("");
		list.add(eat2);

		Status eat3 = new Status();
		eat3.setActionHolder("Eating");
		eat3.setActionName("Hash Brown");
		eat3.setDatettime("Jul 12, 2014 10:30 pm");
		eat3.setStatus("Breakfast at Mcdonalds");
		eat3.setActionImgUrl(getResources().getDrawable(R.drawable.icon_food));
		eat3.setPostViaImgUrl(getResources().getDrawable(
				R.drawable.activitystatusfeed_heart_gem_supersmall));
		eat3.setStatusImgUrl(getResources().getDrawable(
				R.drawable.food_hashbrown));
		// eat3.setImage("");
		list.add(eat3);

		Status doing = new Status();
		doing.setActionHolder("Doing");
		doing.setActionName("Stationary cycling");
		doing.setDatettime("July 12, 2014 4:55 pm");
		doing.setStatus("day 1 of 365. wish me luck!");
		doing.setActionImgUrl(getResources().getDrawable(
				R.drawable.icon_activity));
		doing.setPostViaImgUrl(getResources().getDrawable(
				R.drawable.activitystatusfeed_icon_small_facebook));
		doing.setStatusImgUrl(null);
		// doing.setImage("");
		list.add(doing);

		Status eat4 = new Status();
		eat4.setActionHolder("Blood pressure level at ");
		eat4.setActionName("110/80");
		eat4.setDatettime("July 12, 2014 8:30 am");
		eat4.setStatus("Yehey! Normal");
		eat4.setActionImgUrl(getResources().getDrawable(
				R.drawable.icon_blood_pressure));
		eat4.setPostViaImgUrl(getResources().getDrawable(
				R.drawable.activitystatusfeed_icon_small_facebook));
		eat4.setStatusImgUrl(null);
		// eat4.setImage("");
		list.add(eat4);

		Status eat5 = new Status();
		eat5.setActionHolder("Writing ");
		eat5.setActionName("My Medicine Checklist");
		eat5.setDatettime("July 12, 2014 7:45 am");
		eat5.setStatus("Many medicine ><");
		eat5.setActionImgUrl(getResources().getDrawable(R.drawable.icon_note));
		eat5.setPostViaImgUrl(getResources().getDrawable(
				R.drawable.activitystatusfeed_heart_gem_supersmall));
		eat5.setStatusImgUrl(getResources().getDrawable(
				R.drawable.sample_image_pills));
		// eat5.setImage("");
		list.add(eat5);

		Status drink2 = new Status();
		drink2.setActionHolder("Blood sugar level at ");
		drink2.setActionName("7.5 mmol/L");
		drink2.setDatettime("July 12, 2014 8:00 am");
		drink2.setStatus("good morning!");
		drink2.setActionImgUrl(getResources().getDrawable(
				R.drawable.icon_blood_sugar));
		drink2.setPostViaImgUrl(getResources().getDrawable(
				R.drawable.activitystatusfeed_heart_gem_supersmall));
		drink2.setStatusImgUrl(null);
		// drink2.setImage("");
		list.add(drink2);

		return list;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		int itemPosition = position;
		String itemValue = (String) l.getItemAtPosition(position);
	}

}
