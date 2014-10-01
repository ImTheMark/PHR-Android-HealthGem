package com.example.phr.mobile.daoimpl;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.ImageHandlerException;
import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileActivityDao;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.tools.ImageHandler;

public class MobileActivityDaoImpl implements MobileActivityDao {

	@Override
	public void add(ActivityTrackerEntry activity) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.ACT_ID, activity.getEntryID());
		values.put(DatabaseHandler.ACT_DATEADDED, fmt.format(activity.getTimestamp()));
		values.put(DatabaseHandler.ACT_ACTIVITYID, activity.getActivity().getEntryID());
		//values.put(DatabaseHandler.ACT_DURATION, activity.getCalorisBurnedPerHour());
		values.put(DatabaseHandler.ACT_CALORIEBURNED, activity.getCalorisBurnedPerHour());
		values.put(DatabaseHandler.ACT_STATUS, activity.getStatus());
		
		try {
			if (activity.getImage().getFileName() == null
					&& activity.getImage().getEncodedImage() != null) {
				String encoded = activity.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				activity.getImage().setFileName(fileName);
			}
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (activity.getImage().getFileName() != null)
			values.put(DatabaseHandler.ACT_PHOTO, activity.getImage().getFileName());
		if (activity.getFbPost() != null)
			values.put(DatabaseHandler.ACT_FBPOSTID, activity.getFbPost().getId());

		db.insert(DatabaseHandler.TABLE_ACTIVITY, null, values);
		db.close();
		
	}

	@Override
	public void edit(ActivityTrackerEntry activity) throws DataAccessException, EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.ACT_ID, activity.getEntryID());
		values.put(DatabaseHandler.ACT_DATEADDED, fmt.format(activity.getTimestamp()));
		values.put(DatabaseHandler.ACT_ACTIVITYID, activity.getActivity().getEntryID());
		//values.put(DatabaseHandler.ACT_DURATION, activity.getCalorisBurnedPerHour());
		values.put(DatabaseHandler.ACT_CALORIEBURNED, activity.getCalorisBurnedPerHour());
		values.put(DatabaseHandler.ACT_STATUS, activity.getStatus());
		
		try {
			if (activity.getImage().getFileName() == null
					&& activity.getImage().getEncodedImage() != null) {
				String encoded = activity.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				activity.getImage().setFileName(fileName);
			}
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (activity.getImage().getFileName() != null)
			values.put(DatabaseHandler.ACT_PHOTO, activity.getImage().getFileName());
		if (activity.getFbPost() != null)
			values.put(DatabaseHandler.ACT_FBPOSTID, activity.getFbPost().getId());

		db.update(DatabaseHandler.TABLE_ACTIVITY, values, DatabaseHandler.ACT_ID + "=" + activity.getEntryID(), null);
		db.close();
	}

	@Override
	public void delete(ActivityTrackerEntry activity) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_ACTIVITY, DatabaseHandler.ACT_ID + "=" + activity.getEntryID(), null);
		db.close();
	}

	@Override
	public ArrayList<ActivityTrackerEntry> getAll() throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addActivityListEntryReturnEntryID(Activity activity) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Activity> getAllActivity() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
