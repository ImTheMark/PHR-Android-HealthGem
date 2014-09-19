package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.phr.adapter.ActivityAdapter;
import com.example.phr.adapter.StatusAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.model.Status;

public class ActivitiesTrackerActivity extends Activity {
	
	ActivityAdapter activityAdapter;
	ListView mActivityList;
	ImageView mBtnAddActivity;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activities_tracker);
		setTitle("Activity Tracker");
		mActivityList = (ListView) findViewById(R.id.listViewActivityTracker);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		activityAdapter = new ActivityAdapter(getApplicationContext(), generateData());
		mActivityList.setAdapter(activityAdapter);
		
		mBtnAddActivity = (ImageView) findViewById(R.id.btnAddActivity);
		mBtnAddActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), NewStatusActivity.class);
				i.putExtra("tracker",TrackerInputType.ACTIVITY);
				startActivity(i);
			}
		});
	}

	private List<com.example.phr.model.Activity> generateData() {
		List<com.example.phr.model.Activity> list = new ArrayList<com.example.phr.model.Activity>();
		
		com.example.phr.model.Activity activity1 = new com.example.phr.model.Activity();
		activity1.setAction("Stationary cycling");
		activity1.setCalBurned("246");
		activity1.setDate("Jul 12, 2012");
		activity1.setTime("4:55 PM");
		activity1.setDuration("30 Mins");
		
		com.example.phr.model.Activity activity2 = new com.example.phr.model.Activity();
		activity2.setAction("Weight lifting ");
		activity2.setCalBurned("211");
		activity2.setDate("Jul 10, 2012");
		activity2.setTime("5:50 PM");
		activity2.setDuration("30 Mins");
		
		com.example.phr.model.Activity activity3= new com.example.phr.model.Activity();
		activity3.setAction("Jogging");
		activity3.setCalBurned("563");
		activity3.setDate("Jul 19, 2012");
		activity3.setTime("8:00 AM");
		activity3.setDuration("1 hr");
		
		com.example.phr.model.Activity activity4= new com.example.phr.model.Activity();
		activity4.setAction("Swimming");
		activity4.setCalBurned("493");
		activity4.setDate("Jun 20, 2012");
		activity4.setTime("5:55PM");
		activity4.setDuration("1 hr");
		
		com.example.phr.model.Activity activity5= new com.example.phr.model.Activity();
		activity5.setAction("Jogging");
		activity5.setCalBurned("563");
		activity5.setDate("Jun 05, 2012");
		activity5.setTime("6:55 PM");
		activity5.setDuration("1 hr");
		
		com.example.phr.model.Activity activity6= new com.example.phr.model.Activity();
		activity6.setAction("Jogging");
		activity6.setCalBurned("563");
		activity6.setDate("Jun 02, 2012");
		activity6.setTime("5:55 sPM");
		activity6.setDuration("1 hr");
		
		list.add(activity1);
		list.add(activity2);
		list.add(activity3);
		list.add(activity4);
		list.add(activity5);
		list.add(activity6);
		
		/*		Status activity1 = new Status();
		activity1.setActionHolder("Doing");
		activity1.setActionName("swimming");
		activity1.setDatettime("January 12, 2014 12:34AM");
		activity1.setStatus("YUM YUM YUM :)))");
		activity1.setActionImgUrl(getResources().getDrawable(R.drawable.icon_activity));
		activity1.setPostViaImgUrl(getResources().getDrawable(R.drawable.icon_small_facebook));
		list.add(activity1);
		
		Status activity2 = new Status();
		activity2.setActionHolder("Doing");
		activity2.setActionName("reading a book");
		activity2.setDatettime("January 12, 2014 12:34AM");
		activity2.setStatus("weeeeeeeeeeeeeee");
		activity2.setActionImgUrl(getResources().getDrawable(R.drawable.icon_activity));
		activity2.setPostViaImgUrl(getResources().getDrawable(R.drawable.icon_small_facebook));
		list.add(activity2);
		
		Status activity3 = new Status();
		activity3.setActionHolder("Doing");
		activity3.setActionName("fishing");
		activity3.setDatettime("January 12, 2014 12:34AM");
		activity3.setStatus("eat today die tom");
		activity3.setActionImgUrl(getResources().getDrawable(R.drawable.icon_activity));
		activity3.setPostViaImgUrl(getResources().getDrawable(R.drawable.icon_small_facebook));
		list.add(activity3);
		
		Status activity4 = new Status();
		activity4.setActionHolder("hatching an egg");
		activity4.setActionName("coffee");
		activity4.setDatettime("January 12, 2014 12:34AM");
		activity4.setStatus("good morning!");
		activity4.setActionImgUrl(getResources().getDrawable(R.drawable.icon_activity));
		activity4.setPostViaImgUrl(getResources().getDrawable(R.drawable.icon_small_facebook));
		list.add(activity4);
		

		list.add(activity1);
		list.add(activity2);
		list.add(activity3);
		list.add(activity4);
		list.add(activity1);
		list.add(activity2);
		list.add(activity3);
		list.add(activity4);
		list.add(activity1);
		list.add(activity2);
		list.add(activity3);
		list.add(activity4);
		list.add(activity1);
		list.add(activity2);
		list.add(activity3);
		list.add(activity4);
		list.add(activity1);
		list.add(activity2);
		list.add(activity3);
		list.add(activity4);*/
		
		return list;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_tracker_help, menu);
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
