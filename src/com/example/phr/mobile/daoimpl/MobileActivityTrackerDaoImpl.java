package com.example.phr.mobile.daoimpl;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.ImageHandlerException;
import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileActivityTrackerDao;
import com.example.phr.mobile.models.ActivitySingle;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.FBPost;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileActivityTrackerDaoImpl implements MobileActivityTrackerDao {

	@Override
	public void add(ActivityTrackerEntry activity) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.ACT_ID, activity.getEntryID());
		values.put(DatabaseHandler.ACT_DATEADDED,
				fmt.format(activity.getTimestamp()));
		values.put(DatabaseHandler.ACT_ACTIVITYID, activity.getActivity()
				.getEntryID());

		if (!activityListEntryExists(db, activity.getActivity().getEntryID()))
			addActivityListEntry(db, activity.getActivity());

		// values.put(DatabaseHandler.ACT_DURATION,
		// activity.getCalorisBurnedPerHour());
		values.put(DatabaseHandler.ACT_CALORIEBURNED,
				activity.getCalorisBurnedPerHour());
		values.put(DatabaseHandler.ACT_STATUS, activity.getStatus());

		try {
			if (activity.getImage() != null) {
				String encoded = activity.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				activity.getImage().setFileName(fileName);
				values.put(DatabaseHandler.ACT_PHOTO, activity.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.ACT_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (activity.getFbPost() != null)
			values.put(DatabaseHandler.ACT_FBPOSTID, activity.getFbPost()
					.getId());

		db.insert(DatabaseHandler.TABLE_ACTIVITY, null, values);
		db.close();

	}

	@Override
	public void edit(ActivityTrackerEntry activity) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.ACT_ID, activity.getEntryID());
		values.put(DatabaseHandler.ACT_DATEADDED,
				fmt.format(activity.getTimestamp()));
		values.put(DatabaseHandler.ACT_ACTIVITYID, activity.getActivity()
				.getEntryID());
		// values.put(DatabaseHandler.ACT_DURATION,
		// activity.getCalorisBurnedPerHour());
		values.put(DatabaseHandler.ACT_CALORIEBURNED,
				activity.getCalorisBurnedPerHour());
		values.put(DatabaseHandler.ACT_STATUS, activity.getStatus());

		try {
			if (activity.getImage().getFileName() == null) {
				String encoded = activity.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				activity.getImage().setFileName(fileName);
				values.put(DatabaseHandler.ACT_PHOTO, activity.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.ACT_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (activity.getFbPost() != null)
			values.put(DatabaseHandler.ACT_FBPOSTID, activity.getFbPost()
					.getId());

		db.update(DatabaseHandler.TABLE_ACTIVITY, values,
				DatabaseHandler.ACT_ID + "=" + activity.getEntryID(), null);
		db.close();
	}

	@Override
	public ArrayList<ActivityTrackerEntry> getAll() throws DataAccessException {
		ArrayList<ActivityTrackerEntry> actList = new ArrayList<ActivityTrackerEntry>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_ACTIVITY;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				try {
					Timestamp timestamp = DateTimeParser.getTimestamp(cursor
							.getString(1));

					PHRImage image = new PHRImage();

					if (cursor.getString(6) == null)
						image = null;
					else {
						image.setFileName(cursor.getString(6));
						Bitmap bitmap = ImageHandler.loadImage(image
								.getFileName());
						String encoded = ImageHandler
								.encodeImageToBase64(bitmap);
						image.setEncodedImage(encoded);
					}

					ActivityTrackerEntry act = new ActivityTrackerEntry(
							cursor.getInt(0), new FBPost(cursor.getInt(7)),
							timestamp, cursor.getString(5), image,
							getActivityListEntry(db, cursor.getInt(2)),
							cursor.getDouble(4));
					actList.add(act);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				} catch (DataAccessException e) {
					e.printStackTrace();
				}

			} while (cursor.moveToNext());
		}

		db.close();
		return actList;
	}

	@Override
	public List<ActivityTrackerEntry> getAllReversed()
			throws DataAccessException {
		ArrayList<ActivityTrackerEntry> actList = new ArrayList<ActivityTrackerEntry>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_ACTIVITY
				+ " ORDER BY " + DatabaseHandler.ACT_DATEADDED + " DESC";

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				try {
					Timestamp timestamp = DateTimeParser.getTimestamp(cursor
							.getString(1));

					PHRImage image = new PHRImage();

					if (cursor.getString(6) == null)
						image = null;
					else {
						image.setFileName(cursor.getString(6));
						Bitmap bitmap = ImageHandler.loadImage(image
								.getFileName());
						String encoded = ImageHandler
								.encodeImageToBase64(bitmap);
						image.setEncodedImage(encoded);
					}

					ActivityTrackerEntry act = new ActivityTrackerEntry(
							cursor.getInt(0), new FBPost(cursor.getInt(7)),
							timestamp, cursor.getString(5), image,
							getActivityListEntry(db, cursor.getInt(2)),
							cursor.getDouble(4));
					actList.add(act);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				} catch (DataAccessException e) {
					e.printStackTrace();
				}

			} while (cursor.moveToNext());
		}

		db.close();
		return actList;
	}

	@Override
	public void delete(ActivityTrackerEntry activity)
			throws DataAccessException, EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_ACTIVITY, DatabaseHandler.ACT_ID + "="
				+ activity.getEntryID(), null);
		db.close();
	}

	@Override
	public void addActivityListEntry(SQLiteDatabase db, ActivitySingle activity) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.ACTLIST_ID, activity.getEntryID());
		values.put(DatabaseHandler.ACTLIST_NAME, activity.getName());
		values.put(DatabaseHandler.ACTLIST_MET, activity.getMET());
		db.insert(DatabaseHandler.TABLE_ACTIVITYLIST, null, values);
	}

	@Override
	public ArrayList<ActivitySingle> getAllActivityListEntry()
			throws DataAccessException {
		ArrayList<ActivitySingle> actList = new ArrayList<ActivitySingle>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_ACTIVITYLIST;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				actList.add(new ActivitySingle(cursor.getInt(0), cursor
						.getString(2), cursor.getDouble(3)));
			} while (cursor.moveToNext());
		}

		db.close();
		return actList;
	}

	@Override
	public Boolean activityListEntryExists(SQLiteDatabase db, Integer activityID)
			throws DataAccessException {
		Boolean bool = false;
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_ACTIVITYLIST + " WHERE "
				+ DatabaseHandler.ACTLIST_ID + " = " + activityID;

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst())
			bool = true;

		return bool;
	}

	@Override
	public ActivitySingle getActivityListEntry(SQLiteDatabase db,
			Integer activityID) throws DataAccessException {
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_ACTIVITYLIST + " WHERE "
				+ DatabaseHandler.ACTLIST_ID + " = " + activityID;

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			ActivitySingle act = new ActivitySingle(cursor.getInt(0),
					cursor.getString(1), cursor.getDouble(2));
			return act;
		}
		return null;
	}

	@Override
	public ActivityTrackerEntry getLatest() {
		// TODO Auto-generated method stub
		return null;
	}
}
