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
import com.example.phr.mobile.dao.MobileBloodPressureTrackerDao;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileBloodPressureTrackerDaoImpl implements
		MobileBloodPressureTrackerDao {

	@Override
	public void add(BloodPressure bp) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BP_ID, bp.getEntryID());
		values.put(DatabaseHandler.BP_DATEADDED, fmt.format(bp.getTimestamp()));
		values.put(DatabaseHandler.BP_SYSTOLIC, bp.getSystolic());
		values.put(DatabaseHandler.BP_DIASTOLIC, bp.getDiastolic());
		values.put(DatabaseHandler.BP_STATUS, bp.getStatus());

		try {
			if (bp.getImage() != null) {
				String encoded = bp.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				bp.getImage().setFileName(fileName);
				values.put(DatabaseHandler.BP_PHOTO, bp.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.BP_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (bp.getFacebookID() != null)
			values.put(DatabaseHandler.BP_FBPOSTID, bp.getFacebookID());

		db.insert(DatabaseHandler.TABLE_BLOODPRESSURE, null, values);
		db.close();
	}

	@Override
	public void edit(BloodPressure bp) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BP_DATEADDED, fmt.format(bp.getTimestamp()));
		values.put(DatabaseHandler.BP_SYSTOLIC, bp.getSystolic());
		values.put(DatabaseHandler.BP_DIASTOLIC, bp.getDiastolic());
		values.put(DatabaseHandler.BP_STATUS, bp.getStatus());

		try {
			if (bp.getImage() != null) {
				String encoded = bp.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				bp.getImage().setFileName(fileName);
				values.put(DatabaseHandler.BP_PHOTO, bp.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.BP_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (bp.getFacebookID() != null)
			values.put(DatabaseHandler.BP_FBPOSTID, bp.getFacebookID());

		db.update(DatabaseHandler.TABLE_BLOODPRESSURE, values,
				DatabaseHandler.BP_ID + "=" + bp.getEntryID(), null);
		db.close();
	}

	@Override
	public ArrayList<BloodPressure> getAll() throws DataAccessException {
		ArrayList<BloodPressure> bpList = new ArrayList<BloodPressure>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_BLOODPRESSURE;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Timestamp timestamp;
				try {
					timestamp = DateTimeParser
							.getTimestamp(cursor.getString(1));
				} catch (ParseException e) {
					throw new DataAccessException(
							"An error has occured while trying to access data",
							e);
				}
				PHRImage image = new PHRImage();
				if (cursor.getString(5) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(5));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				}
				BloodPressure bp = new BloodPressure(cursor.getInt(0),
						timestamp, cursor.getString(4), image,
						cursor.getInt(2), cursor.getInt(3));

				bpList.add(bp);
			} while (cursor.moveToNext());
		}

		db.close();
		return bpList;
	}

	@Override
	public void delete(BloodPressure bp) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		if (bp.getImage() != null)
			ImageHandler.deleteImage(bp.getImage().getFileName());
		db.delete(DatabaseHandler.TABLE_BLOODPRESSURE, DatabaseHandler.BP_ID
				+ "=" + bp.getEntryID(), null);
		db.close();
	}

	@Override
	public List<BloodPressure> getAllReversed() throws DataAccessException {
		List<BloodPressure> bpList = new ArrayList<BloodPressure>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_BLOODPRESSURE + " ORDER BY "
				+ DatabaseHandler.BP_DATEADDED + " DESC";

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Timestamp timestamp;
				try {
					timestamp = DateTimeParser
							.getTimestamp(cursor.getString(1));
				} catch (ParseException e) {
					throw new DataAccessException(
							"An error has occured while trying to access data",
							e);
				}
				PHRImage image = new PHRImage();
				if (cursor.getString(5) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(5));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				}
				BloodPressure bp = new BloodPressure(cursor.getInt(0),
						timestamp, cursor.getString(4), image,
						cursor.getInt(2), cursor.getInt(3));

				bpList.add(bp);
			} while (cursor.moveToNext());
		}

		db.close();
		return bpList;
	}

	@Override
	public BloodPressure getLatest() throws DataAccessException {
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_BLOODPRESSURE + " ORDER BY "
				+ DatabaseHandler.BP_DATEADDED + " DESC LIMIT 1";

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			Timestamp timestamp;
			try {
				timestamp = DateTimeParser.getTimestamp(cursor.getString(1));
			} catch (ParseException e) {
				throw new DataAccessException(
						"An error has occured while trying to access data", e);
			}
			PHRImage image = new PHRImage();
			if (cursor.getString(5) == null)
				image = null;
			else {
				image.setFileName(cursor.getString(5));
				Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
			}
			BloodPressure bp = new BloodPressure(cursor.getInt(0), timestamp,
					cursor.getString(4), image, cursor.getInt(2),
					cursor.getInt(3));
			return bp;
		}

		db.close();
		return null;
	}
}
