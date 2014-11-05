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
import com.example.phr.mobile.dao.MobileBloodSugarTrackerDao;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileBloodSugarTrackerDaoImpl implements
		MobileBloodSugarTrackerDao {

	@Override
	public void add(BloodSugar bloodSugar) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BS_ID, bloodSugar.getEntryID());
		values.put(DatabaseHandler.BS_DATEADDED,
				fmt.format(bloodSugar.getTimestamp()));
		values.put(DatabaseHandler.BS_BLOODSUGAR, bloodSugar.getBloodSugar());
		values.put(DatabaseHandler.BS_TYPE, bloodSugar.getType());
		values.put(DatabaseHandler.BS_STATUS, bloodSugar.getStatus());

		try {
			if (bloodSugar.getImage() != null) {
				String encoded = bloodSugar.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				bloodSugar.getImage().setFileName(fileName);
				values.put(DatabaseHandler.BS_PHOTO, bloodSugar.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.BS_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}

		if (bloodSugar.getFacebookID() != null)
			values.put(DatabaseHandler.BS_FBPOSTID, bloodSugar.getFacebookID());

		db.insert(DatabaseHandler.TABLE_BLOODSUGAR, null, values);
		db.close();
	}

	@Override
	public void edit(BloodSugar bloodSugar) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BS_DATEADDED,
				fmt.format(bloodSugar.getTimestamp()));
		values.put(DatabaseHandler.BS_BLOODSUGAR, bloodSugar.getBloodSugar());
		values.put(DatabaseHandler.BS_TYPE, bloodSugar.getType());
		values.put(DatabaseHandler.BS_STATUS, bloodSugar.getStatus());

		try {
			if (bloodSugar.getImage() != null) {
				String encoded = bloodSugar.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				bloodSugar.getImage().setFileName(fileName);
				values.put(DatabaseHandler.BS_PHOTO, bloodSugar.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.BS_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}

		if (bloodSugar.getFacebookID() != null)
			values.put(DatabaseHandler.BS_FBPOSTID, bloodSugar.getFacebookID());

		db.update(DatabaseHandler.TABLE_BLOODSUGAR, values,
				DatabaseHandler.BS_ID + "=" + bloodSugar.getEntryID(), null);
		db.close();

	}

	@Override
	public ArrayList<BloodSugar> getAll() throws DataAccessException {
		ArrayList<BloodSugar> bsList = new ArrayList<BloodSugar>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_BLOODSUGAR;

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
							"Cannot complete operation due to parse failure", e);
				}

				PHRImage image = new PHRImage();

				if (cursor.getString(5) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(5));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				}

				BloodSugar bs = new BloodSugar(cursor.getInt(0),
						cursor.getString(6), timestamp, cursor.getString(4),
						image, cursor.getDouble(2), cursor.getString(3));

				bsList.add(bs);
			} while (cursor.moveToNext());
		}

		db.close();
		return bsList;
	}

	@Override
	public void delete(BloodSugar bloodSugar) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		if (bloodSugar.getImage() != null)
			ImageHandler.deleteImage(bloodSugar.getImage().getFileName());
		db.delete(DatabaseHandler.TABLE_BLOODSUGAR, DatabaseHandler.BS_ID + "="
				+ bloodSugar.getEntryID(), null);
		db.close();
	}

	@Override
	public List<BloodSugar> getAllReversed() throws DataAccessException {
		List<BloodSugar> bsList = new ArrayList<BloodSugar>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_BLOODSUGAR + " ORDER BY "
				+ DatabaseHandler.BS_DATEADDED + " DESC";

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
							"Cannot complete operation due to parse failure", e);
				}

				PHRImage image = new PHRImage();

				if (cursor.getString(5) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(5));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				}

				BloodSugar bs = new BloodSugar(cursor.getInt(0),
						cursor.getString(6), timestamp, cursor.getString(4),
						image, cursor.getDouble(2), cursor.getString(3));

				bsList.add(bs);
			} while (cursor.moveToNext());
		}

		db.close();
		return bsList;
	}

	@Override
	public BloodSugar getLatest() throws DataAccessException {
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_BLOODSUGAR + " ORDER BY "
				+ DatabaseHandler.BS_DATEADDED + " DESC LIMIT 1";

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			Timestamp timestamp;
			try {
				timestamp = DateTimeParser.getTimestamp(cursor.getString(1));
			} catch (ParseException e) {
				throw new DataAccessException(
						"Cannot complete operation due to parse failure", e);
			}

			PHRImage image = new PHRImage();

			if (cursor.getString(5) == null)
				image = null;
			else {
				image.setFileName(cursor.getString(5));
				Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
			}

			BloodSugar bs = new BloodSugar(cursor.getInt(0),
					cursor.getString(6), timestamp, cursor.getString(4), image,
					cursor.getDouble(2), cursor.getString(3));
			return bs;
		}

		db.close();
		return null;
	}

}