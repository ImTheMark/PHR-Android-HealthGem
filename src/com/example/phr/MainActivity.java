package com.example.phr;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.phr.adapter.TabsPagerAdapter;
import com.example.phr.enums.TrackerInputType;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	final static int RQS_1 = 1;

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private ScheduleClient scheduleClient;
	// Tab titles
	private final String[] tabs = { "Summary Report", "Timeline", "Tracker",
			"About Me" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// actionBar.setDisplayShowTitleEnabled(false);
		// actionBar.setDisplayShowHomeEnabled(false);
		// Adding Tabs
		/*
		 * for (String tab_name : tabs) {
		 * actionBar.addTab(actionBar.newTab().setText(tab_name)
		 * .setTabListener(this)); }
		 */
		for (int x = 0; x < tabs.length; x++) {
			switch (x) {
			case 0:
				actionBar
						.addTab(actionBar
								.newTab()
								.setIcon(
										R.drawable.activitymain_selector_summary_report)
								.setTabListener(this));
				break;
			case 1:
				actionBar.addTab(actionBar.newTab()
						.setIcon(R.drawable.activitymain_selector_journal)
						.setTabListener(this));
				break;
			case 2:
				actionBar
						.addTab(actionBar
								.newTab()
								.setIcon(
										R.drawable.activitymain_selector_health_tracker)
								.setTabListener(this));
				break;
			case 3:
				actionBar.addTab(actionBar.newTab()
						.setIcon(R.drawable.activitymain_selector_about_me)
						.setTabListener(this));
				break;
			}

		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@SuppressLint("NewApi")
			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
				setTitle(tabs[position]);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		// check if user post blood sugar today
		Calendar bsAlarmTime = Calendar.getInstance();
		bsAlarmTime.set(Calendar.HOUR_OF_DAY, 14);
		bsAlarmTime.set(Calendar.MINUTE, 0);
		bsAlarmTime.set(Calendar.SECOND, 0);
		setAlarm(bsAlarmTime, TrackerInputType.BLOOD_SUGAR);

		// check if user post blood pressure today
		Calendar bpAlarmTime = Calendar.getInstance();
		bpAlarmTime.set(Calendar.HOUR_OF_DAY, 10);
		bpAlarmTime.set(Calendar.MINUTE, 0);
		bpAlarmTime.set(Calendar.SECOND, 0);
		setAlarm(bpAlarmTime, TrackerInputType.BLOOD_PRESSURE);

		// check if user post food today in fb, if not notif user to post food
		Calendar foodAlarmTime = Calendar.getInstance();
		foodAlarmTime.set(Calendar.HOUR_OF_DAY, 20);
		foodAlarmTime.set(Calendar.MINUTE, 0);
		foodAlarmTime.set(Calendar.SECOND, 0);
		setAlarm(foodAlarmTime, TrackerInputType.FOOD);

		// check if user post activty this week at this time
		Calendar activityAlarmTime = Calendar.getInstance();
		activityAlarmTime.set(Calendar.HOUR_OF_DAY, 18);
		activityAlarmTime.set(Calendar.MINUTE, 0);
		activityAlarmTime.set(Calendar.SECOND, 0);
		setAlarm(activityAlarmTime, TrackerInputType.ACTIVITY);

		// check if user post weight between this 2 weeks at this time
		Calendar weightAlarmTime = Calendar.getInstance();
		weightAlarmTime.set(Calendar.HOUR_OF_DAY, 18);
		weightAlarmTime.set(Calendar.MINUTE, 0);
		weightAlarmTime.set(Calendar.SECOND, 0);
		setAlarm(weightAlarmTime, TrackerInputType.WEIGHT);

		// check if user post weight between this 6 months at this time
		Calendar checkupAlarmTime = Calendar.getInstance();
		checkupAlarmTime.set(Calendar.HOUR_OF_DAY, 18);
		checkupAlarmTime.set(Calendar.MINUTE, 0);
		checkupAlarmTime.set(Calendar.SECOND, 0);
		setAlarm(checkupAlarmTime, TrackerInputType.CHECKUP);

	}

	private void setAlarm(Calendar targetCal, String tracker) {

		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		if (tracker.equals(TrackerInputType.BLOOD_SUGAR))
			intent.putExtra("tracker", TrackerInputType.BLOOD_SUGAR);
		else if (tracker.equals(TrackerInputType.BLOOD_PRESSURE))
			intent.putExtra("tracker", TrackerInputType.BLOOD_PRESSURE);
		else if (tracker.equals(TrackerInputType.WEIGHT))
			intent.putExtra("tracker", TrackerInputType.WEIGHT);
		else if (tracker.equals(TrackerInputType.CHECKUP))
			intent.putExtra("tracker", TrackerInputType.CHECKUP);
		else if (tracker.equals(TrackerInputType.FOOD))
			intent.putExtra("tracker", TrackerInputType.FOOD);
		else if (tracker.equals(TrackerInputType.ACTIVITY))
			intent.putExtra("tracker", TrackerInputType.ACTIVITY);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getBaseContext(), RQS_1, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
		// targetCal.getTimeInMillis(),
		// AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				targetCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				pendingIntent);
		// alarmManager.set(AlarmManager.RTC_WAKEUP,
		// targetCal.getTimeInMillis(),
		// pendingIntent);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@SuppressLint("NewApi")
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activitymain_menu_settings, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(getApplicationContext(),
					SettingsActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_notifications:
			Intent intent2 = new Intent(getApplicationContext(),
					VerificationActivity.class);
			startActivity(intent2);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
