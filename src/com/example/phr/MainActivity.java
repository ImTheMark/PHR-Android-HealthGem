package com.example.phr;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phr.adapter.TabsPagerAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.service.VerificationService;
import com.example.phr.serviceimpl.VerificationServiceImpl;

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
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
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

		long _alarm = 0;
		Calendar now = Calendar.getInstance();

		// check if user post blood pressure today
		Calendar bpAlarmTime = Calendar.getInstance();
		bpAlarmTime.set(Calendar.HOUR_OF_DAY, 10);
		bpAlarmTime.set(Calendar.MINUTE, 0);
		bpAlarmTime.set(Calendar.SECOND, 0);
		if (bpAlarmTime.getTimeInMillis() <= now.getTimeInMillis()) {
			_alarm = bpAlarmTime.getTimeInMillis()
					+ (AlarmManager.INTERVAL_DAY + 1);
		} else {
			_alarm = bpAlarmTime.getTimeInMillis();
		}
		setAlarm(_alarm, TrackerInputType.BLOOD_PRESSURE, 1);

		// check if user post blood sugar today
		Calendar bsAlarmTime = Calendar.getInstance();
		bsAlarmTime.set(Calendar.HOUR_OF_DAY, 14);
		bsAlarmTime.set(Calendar.MINUTE, 0);
		bsAlarmTime.set(Calendar.SECOND, 0);
		if (bsAlarmTime.getTimeInMillis() <= now.getTimeInMillis()) {
			_alarm = bsAlarmTime.getTimeInMillis()
					+ (AlarmManager.INTERVAL_DAY + 1);
		} else {
			_alarm = bsAlarmTime.getTimeInMillis();
		}
		setAlarm(_alarm, TrackerInputType.BLOOD_SUGAR, 2);

		// check if user post weight between this 6 months at this time
		Calendar checkupAlarmTime = Calendar.getInstance();
		checkupAlarmTime.set(Calendar.HOUR_OF_DAY, 16);
		checkupAlarmTime.set(Calendar.MINUTE, 0);
		checkupAlarmTime.set(Calendar.SECOND, 0);
		if (checkupAlarmTime.getTimeInMillis() <= now.getTimeInMillis()) {
			_alarm = checkupAlarmTime.getTimeInMillis()
					+ (AlarmManager.INTERVAL_DAY + 1);
		} else {
			_alarm = checkupAlarmTime.getTimeInMillis();
		}
		setAlarm(_alarm, TrackerInputType.CHECKUP, 3);

		// check if user post weight between this 2 weeks at this time
		Calendar weightAlarmTime = Calendar.getInstance();
		weightAlarmTime.set(Calendar.HOUR_OF_DAY, 17);
		weightAlarmTime.set(Calendar.MINUTE, 0);
		weightAlarmTime.set(Calendar.SECOND, 0);
		if (weightAlarmTime.getTimeInMillis() <= now.getTimeInMillis()) {
			_alarm = weightAlarmTime.getTimeInMillis()
					+ (AlarmManager.INTERVAL_DAY + 1);
		} else {
			_alarm = weightAlarmTime.getTimeInMillis();
		}
		setAlarm(_alarm, TrackerInputType.WEIGHT, 4);

		// check if there's entry for verify
		Calendar verifyAlarmTime = Calendar.getInstance();
		verifyAlarmTime.set(Calendar.HOUR_OF_DAY, 18);
		verifyAlarmTime.set(Calendar.MINUTE, 0);
		verifyAlarmTime.set(Calendar.SECOND, 0);
		if (verifyAlarmTime.getTimeInMillis() <= now.getTimeInMillis()) {
			_alarm = verifyAlarmTime.getTimeInMillis()
					+ (AlarmManager.INTERVAL_DAY + 1);
		} else {
			_alarm = verifyAlarmTime.getTimeInMillis();
		}
		setAlarm(_alarm, TrackerInputType.VERIFY, 5);

		// check if user post activty this week at this time
		Calendar activityAlarmTime = Calendar.getInstance();
		activityAlarmTime.set(Calendar.HOUR_OF_DAY, 19);
		activityAlarmTime.set(Calendar.MINUTE, 0);
		activityAlarmTime.set(Calendar.SECOND, 0);
		if (activityAlarmTime.getTimeInMillis() <= now.getTimeInMillis()) {
			_alarm = activityAlarmTime.getTimeInMillis()
					+ (AlarmManager.INTERVAL_DAY + 1);
		} else {
			_alarm = activityAlarmTime.getTimeInMillis();
		}
		setAlarm(_alarm, TrackerInputType.ACTIVITY, 6);

		// check if user post food today in fb, if not notif user to post food
		Calendar foodAlarmTime = Calendar.getInstance();
		foodAlarmTime.set(Calendar.HOUR_OF_DAY, 20);
		foodAlarmTime.set(Calendar.MINUTE, 0);
		foodAlarmTime.set(Calendar.SECOND, 0);
		if (foodAlarmTime.getTimeInMillis() <= now.getTimeInMillis()) {
			_alarm = foodAlarmTime.getTimeInMillis()
					+ (AlarmManager.INTERVAL_DAY + 1);
		} else {
			_alarm = foodAlarmTime.getTimeInMillis();
		}
		setAlarm(_alarm, TrackerInputType.FOOD, 7);

		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();

		if (extras != null && in.hasExtra("backToMenu")) {
			int position = in.getExtras().getInt("backToMenu");
			viewPager.setCurrentItem(position);
		}

	}

	private void setAlarm(long targetCal, String tracker, int rqs) {

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
				getBaseContext(), rqs, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
		// targetCal.getTimeInMillis(),
		// AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal,
				AlarmManager.INTERVAL_DAY, pendingIntent);
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
	protected void onResume() {
		super.onResume();
		invalidateOptionsMenu();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activitymain_menu_settings, menu);

		RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(
				R.id.action_notifications).getActionView();
		TextView tv = (TextView) badgeLayout
				.findViewById(R.id.actionbar_notifcation_textview);

		VerificationService vService = new VerificationServiceImpl();

		if (vService.getUnverifiedPostsCount() > 0) {
			tv.setVisibility(View.VISIBLE);
			tv.setText(vService.getUnverifiedPostsCount() + "");
		} else
			tv.setVisibility(View.GONE);

		final Menu m = menu;
		final MenuItem item = menu.findItem(R.id.action_notifications);
		MenuItemCompat.getActionView(item).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.e("main notif icon", "print");
						onOptionsItemSelected(item);
					}
				});

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
			Log.e("main notif icon", "pindot");
			Intent intent2 = new Intent(getApplicationContext(),
					VerificationActivityTest.class);
			startActivity(intent2);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Close Application");
		alertDialogBuilder
				.setMessage("Do you want to close HealthGem?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								moveTaskToBack(true);
								android.os.Process
										.killProcess(android.os.Process.myPid());
								System.exit(1);
							}
						})

				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

}
