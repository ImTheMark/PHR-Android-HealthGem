package com.example.phr;

import java.util.Calendar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * This is our service client, it is the 'middle-man' between the service and
 * any activity that wants to connect to the service
 * 
 * @author paul.blundell
 */
public class ScheduleClient {

	// The hook into our service
	private ScheduleService mBoundService;
	// The context to start the service in
	private final Context mContext;
	// A flag if we are connected to the service or not
	private boolean mIsBound;

	public ScheduleService getmBoundService() {
		return mBoundService;
	}

	public void setmBoundService(ScheduleService mBoundService) {
		this.mBoundService = mBoundService;
	}

	public boolean ismIsBound() {
		return mIsBound;
	}

	public void setmIsBound(boolean mIsBound) {
		this.mIsBound = mIsBound;
	}

	public Context getmContext() {
		return mContext;
	}

	public ServiceConnection getmConnection() {
		return mConnection;
	}

	public ScheduleClient(Context context) {
		mContext = context;
		Log.e("scheduleclient context", context.toString());
	}

	/**
	 * Call this to connect your activity to your service
	 */
	public void doBindService() {
		// Establish a connection with our service
		mContext.bindService(new Intent(mContext, ScheduleService.class),
				mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	/**
	 * When you attempt to connect to the service, this connection will be
	 * called with the result. If we have successfully connected we instantiate
	 * our service object so that we can call methods on it.
	 */
	private final ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with our service has been
			// established,
			// giving us the service object we can use to interact with our
			// service.
			mBoundService = ((ScheduleService.ServiceBinder) service)
					.getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			mBoundService = null;
		}
	};

	/**
	 * Tell our service to set an alarm for the given date
	 * 
	 * @param c
	 *            a date to set the notification for
	 */
	public void setAlarmForNotification(Calendar c) {
		Log.e("set alarm", c.getTime().toString());
		mBoundService.setAlarm(c);
	}

	/**
	 * When you have finished with the service call this method to stop it
	 * releasing your connection and resources
	 */
	public void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			mContext.unbindService(mConnection);
			mIsBound = false;
		}
	}
}