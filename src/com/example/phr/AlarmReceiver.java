package com.example.phr;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.phr.exceptions.ServiceException;
import com.example.phr.serviceimpl.BloodSugarTrackerServiceImpl;

public class AlarmReceiver extends BroadcastReceiver {

	private static final int MY_NOTIFICATION_ID = 1;
	NotificationManager notificationManager;
	Notification myNotification;

	@Override
	public void onReceive(Context context, Intent intent) {

		Intent myIntent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		String title = "HealthGem";
		String content = "what did you eat?";
		String ticker = "What did you eat?";
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		BloodSugarTrackerServiceImpl bsService = new BloodSugarTrackerServiceImpl();
		Log.e("bsservice", "called");
		Date lastBs = null;
		Date currentDate = new Date();

		try {
			lastBs = new Date(bsService.getLatest().getTimestamp().getTime());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (lastBs != null)
			Log.e("bsLast", format.format(lastBs));
		Log.e("currentDate", format.format(currentDate));

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

		myNotification = new NotificationCompat.Builder(context)
				.setContentTitle(title).setContentText(content)
				.setTicker(ticker).setWhen(System.currentTimeMillis())
				.setContentIntent(pendingIntent)
				.setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true)
				.setSmallIcon(R.drawable.heart_gem).build();

		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
	}

}
