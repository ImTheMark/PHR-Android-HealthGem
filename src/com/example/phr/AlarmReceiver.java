package com.example.phr;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.serviceimpl.BloodPressureTrackerServiceImpl;
import com.example.phr.serviceimpl.BloodSugarTrackerServiceImpl;
import com.example.phr.tools.DateTimeParser;

public class AlarmReceiver extends BroadcastReceiver {

	private static final int MY_NOTIFICATION_ID = 1;
	NotificationManager notificationManager;
	Notification myNotification;
	DateFormat dateFormat;
	DateFormat timeFormat;
	DateFormat fmt;
	Calendar calobj;
	Intent myIntent;
	PendingIntent pendingIntent;
	String title;
	String content;
	String ticker;
	Context thisContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		String tracker = intent.getExtras().getString("tracker");
		myIntent = new Intent(context, MainActivity.class);
		pendingIntent = PendingIntent.getActivity(context, 0, myIntent,
				Intent.FLAG_ACTIVITY_NEW_TASK);
		thisContext = context;

		// get current time
		dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
		timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
		calobj = Calendar.getInstance();
		Date date = null;
		try {
			date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timestamp timestamp = new Timestamp(date.getTime());

		// check condition
		if (tracker.equals(TrackerInputType.BLOOD_SUGAR)
				&& showBsNotif(timestamp)) {
			title = "HealthGem";
			content = "What's your sugar level?";
			ticker = "It's time to measure sugar level!";
			showNotification();
		} else if (tracker.equals(TrackerInputType.BLOOD_PRESSURE)
				&& showBpNotif(timestamp)) {
			title = "HealthGem";
			content = "What's your blood pressure?";
			ticker = "It's time to measure blood pressure!";
			showNotification();
		} else if (tracker.equals(TrackerInputType.CHECKUP)
				&& showCheckupNotif(timestamp)) {
			title = "HealthGem";
			content = "Did you check up between this 6 months?";
			ticker = "It's time to have a check up!";
			showNotification();
		} else if (tracker.equals(TrackerInputType.WEIGHT)
				&& showWeightNotif(timestamp)) {
			title = "HealthGem";
			content = "What's your weight?";
			ticker = "It's time to measure your weight!";
			showNotification();
		} else if (tracker.equals(TrackerInputType.FOOD)
				&& showFoodNotif(timestamp)) {
			title = "HealthGem";
			content = "What did you ate?";
			ticker = "It's time record the food you ate!";
			showNotification();
		} else if (tracker.equals(TrackerInputType.ACTIVITY)
				&& showActivityNotif(timestamp)) {
			title = "HealthGem";
			content = "Did you excercise?";
			ticker = "It's time to record your activity!";
			showNotification();
		}

		/*
		 * Date d1 = null; Date d2 = null;
		 * 
		 * try { d1 = format.parse(dateStart); d2 = format.parse(dateStop);
		 * 
		 * //in milliseconds long diff = d2.getTime() - d1.getTime();
		 * 
		 * long diffSeconds = diff / 1000 % 60; long diffMinutes = diff / (60 *
		 * 1000) % 60; long diffHours = diff / (60 * 60 * 1000) % 24; long
		 * diffDays = diff / (24 * 60 * 60 * 1000);
		 * 
		 * System.out.print(diffDays + " days, "); System.out.print(diffHours +
		 * " hours, "); System.out.print(diffMinutes + " minutes, ");
		 * System.out.print(diffSeconds + " seconds.");
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

	}

	public boolean showBsNotif(Timestamp current) {
		boolean notif = true; // show notif

		BloodSugarTrackerServiceImpl bsService = new BloodSugarTrackerServiceImpl();
		Log.e("bsservice", "called");
		BloodSugar lastBs = null;
		try {
			lastBs = bsService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// dont show notif
		if (lastBs != null
				&& DateTimeParser.getMonthDay(lastBs.getTimestamp()).equals(
						DateTimeParser.getMonthDay(current)))
			notif = false;

		return notif;
	}

	public boolean showBpNotif(Timestamp current) {
		boolean notif = true; // show notif

		BloodPressureTrackerServiceImpl bpService = new BloodPressureTrackerServiceImpl();
		Log.e("bpservice", "called");
		BloodPressure lastBp = null;
		try {
			lastBp = bpService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// dont show notif
		if (lastBp != null
				&& DateTimeParser.getMonthDay(lastBp.getTimestamp()).equals(
						DateTimeParser.getMonthDay(current)))
			notif = false;

		return notif;
	}

	public boolean showFoodNotif(Timestamp current) {
		boolean notif = true; // show notif

		return notif;
	}

	public boolean showActivityNotif(Timestamp current) {
		boolean notif = true; // show notif

		return notif;
	}

	public boolean showCheckupNotif(Timestamp current) {
		boolean notif = true; // show notif

		return notif;
	}

	public boolean showWeightNotif(Timestamp current) {
		boolean notif = true; // show notif

		return notif;
	}

	public void showNotification() {
		myNotification = new NotificationCompat.Builder(thisContext)
				.setContentTitle(title).setContentText(content)
				.setTicker(ticker).setWhen(System.currentTimeMillis())
				.setContentIntent(pendingIntent)
				.setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true)
				.setSmallIcon(R.drawable.heart_gem).build();

		notificationManager = (NotificationManager) thisContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
	}

}
