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
import com.example.phr.mobile.dao.MobileActivityDao;
import com.example.phr.mobile.dao.MobileActivityTrackerDao;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.GroupedActivity;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileActivityTrackerDaoImpl implements MobileActivityTrackerDao {

	MobileActivityDao mobileActivityDao = new MobileActivityDaoImpl();

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

		mobileActivityDao.addReturnsEntryId(activity.getActivity());

		// values.put(DatabaseHandler.ACT_DURATION,
		// activity.getCalorisBurnedPerHour());
		values.put(DatabaseHandler.ACT_CALORIEBURNED,
				activity.getCaloriesBurnedPerHour());
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
		if (activity.getFacebookID() != null)
			values.put(DatabaseHandler.ACT_FBPOSTID, activity.getFacebookID());

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
				activity.getCaloriesBurnedPerHour());
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
		if (activity.getFacebookID() != null)
			values.put(DatabaseHandler.ACT_FBPOSTID, activity.getFacebookID());

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
					}

					ActivityTrackerEntry act = new ActivityTrackerEntry(
							cursor.getInt(0), cursor.getString(7), timestamp,
							cursor.getString(5), image,
							mobileActivityDao.get(cursor.getInt(2)),
							cursor.getDouble(4), cursor.getInt(3));
					actList.add(act);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
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
					}

					ActivityTrackerEntry act = new ActivityTrackerEntry(
							cursor.getInt(0), cursor.getString(7), timestamp,
							cursor.getString(5), image,
							mobileActivityDao.get(cursor.getInt(2)),
							cursor.getDouble(4), cursor.getInt(3));
					actList.add(act);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
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
	public ActivityTrackerEntry getLatest() throws DataAccessException {
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_ACTIVITY
				+ " ORDER BY " + DatabaseHandler.ACT_DATEADDED
				+ " DESC LIMIT 1";

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			try {
				Timestamp timestamp = DateTimeParser.getTimestamp(cursor
						.getString(1));

				PHRImage image = new PHRImage();

				if (cursor.getString(6) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(6));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				}

				ActivityTrackerEntry act = new ActivityTrackerEntry(
						cursor.getInt(0), cursor.getString(7), timestamp,
						cursor.getString(5), image,
						mobileActivityDao.get(cursor.getInt(2)),
						cursor.getDouble(4), cursor.getInt(3));
				return act;

			} catch (ParseException e) {
				throw new DataAccessException(
						"Cannot complete operation due to parse failure", e);
			}
		}

		db.close();
		return null;
	}

	@Override
	public List<List<ActivityTrackerEntry>> getAllGroupedByDate()
			throws DataAccessException {
		List<List<ActivityTrackerEntry>> groupedActivityList = new ArrayList<List<ActivityTrackerEntry>>();
		List<ActivityTrackerEntry> actList = new ArrayList<ActivityTrackerEntry>();
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
					}

					ActivityTrackerEntry act = new ActivityTrackerEntry(
							cursor.getInt(0), cursor.getString(7), timestamp,
							cursor.getString(5), image,
							mobileActivityDao.get(cursor.getInt(2)),
							cursor.getDouble(4), cursor.getInt(3));
					actList.add(act);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}

			} while (cursor.moveToNext());
		}

		db.close();

		while (actList.size() != 0) {
			List<ActivityTrackerEntry> actListPerDay = new ArrayList<ActivityTrackerEntry>();
			String monthDay = DateTimeParser.getMonthDay(actList.get(0)
					.getTimestamp());

			do {
				ActivityTrackerEntry f = actList.remove(0);
				actListPerDay.add(f);
			} while (actList.size() != 0
					&& monthDay.equals(DateTimeParser.getMonthDay(actList
							.get(0).getTimestamp())));

			groupedActivityList.add(actListPerDay);
		}

		return groupedActivityList;
	}

	@Override
	public List<ActivityTrackerEntry> getAllFromDate(Timestamp date)
			throws DataAccessException {
		List<ActivityTrackerEntry> actListFromDate = new ArrayList<ActivityTrackerEntry>();
		List<ActivityTrackerEntry> actList = new ArrayList<ActivityTrackerEntry>();
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
					}

					ActivityTrackerEntry act = new ActivityTrackerEntry(
							cursor.getInt(0), cursor.getString(7), timestamp,
							cursor.getString(5), image,
							mobileActivityDao.get(cursor.getInt(2)),
							cursor.getDouble(4), cursor.getInt(3));
					actList.add(act);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}

			} while (cursor.moveToNext());
		}

		db.close();

		Boolean dateHasPassedFromGivenDate = false;

		while (actList.size() != 0 && !dateHasPassedFromGivenDate) {
			ActivityTrackerEntry activity = actList.remove(0);

			if (String.valueOf(
					DateTimeParser.getMonth(actList.get(0).getTimestamp()))
					.equals(String.valueOf(DateTimeParser.getMonth(date)))
					&& String
							.valueOf(
									DateTimeParser.getDay(actList.get(0)
											.getTimestamp()))
							.equals(String.valueOf(DateTimeParser.getDay(date))))
				actListFromDate.add(activity);
			else if (actList.get(0).getTimestamp().after(date))
				dateHasPassedFromGivenDate = true;
		}

		return actListFromDate;
	}

	@Override
	public GroupedActivity getFromDateCalculated(Timestamp date)
			throws DataAccessException {
		List<ActivityTrackerEntry> actListFromDate = new ArrayList<ActivityTrackerEntry>();
		List<ActivityTrackerEntry> actList = new ArrayList<ActivityTrackerEntry>();
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
					}

					ActivityTrackerEntry act = new ActivityTrackerEntry(
							cursor.getInt(0), cursor.getString(7), timestamp,
							cursor.getString(5), image,
							mobileActivityDao.get(cursor.getInt(2)),
							cursor.getDouble(4), cursor.getInt(3));
					actList.add(act);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}

			} while (cursor.moveToNext());
		}

		db.close();

		Boolean dateHasPassedFromGivenDate = false;
		double calBurned = 0;
		Timestamp groupedDate = null;
		while (actList.size() != 0 && !dateHasPassedFromGivenDate) {
			ActivityTrackerEntry activity = actList.remove(0);

			if (String.valueOf(
					DateTimeParser.getMonth(actList.get(0).getTimestamp()))
					.equals(String.valueOf(DateTimeParser.getMonth(date)))
					&& String
							.valueOf(
									DateTimeParser.getDay(actList.get(0)
											.getTimestamp()))
							.equals(String.valueOf(DateTimeParser.getDay(date)))) {
				actListFromDate.add(activity);
				calBurned += activity.getCaloriesBurnedPerHour()
						* (activity.getDurationInSeconds() / 3600);
				// not sure
				groupedDate = activity.getTimestamp();
			} else if (actList.get(0).getTimestamp().after(date))
				dateHasPassedFromGivenDate = true;
		}

		GroupedActivity groupedActivity = new GroupedActivity(groupedDate,
				calBurned);
		return groupedActivity;
	}
}
