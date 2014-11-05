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
import com.example.phr.mobile.dao.MobileCheckupTrackerDao;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileCheckupTrackerDaoImpl implements MobileCheckupTrackerDao {

	@Override
	public void add(CheckUp checkUp) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.CU_ID, checkUp.getEntryID());
		values.put(DatabaseHandler.CU_DATEADDED,
				fmt.format(checkUp.getTimestamp()));
		values.put(DatabaseHandler.CU_PURPOSE, checkUp.getPurpose());
		values.put(DatabaseHandler.CU_DOCTORNAME, checkUp.getDoctorsName());
		values.put(DatabaseHandler.CU_NOTES, checkUp.getNotes());
		values.put(DatabaseHandler.CU_STATUS, checkUp.getStatus());

		try {
			if (checkUp.getImage() != null) {
				String encoded = checkUp.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				checkUp.getImage().setFileName(fileName);
				values.put(DatabaseHandler.CU_PHOTO, checkUp.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.CU_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (checkUp.getFacebookID() != null)
			values.put(DatabaseHandler.CU_FBPOSTID, checkUp.getFacebookID());

		db.insert(DatabaseHandler.TABLE_CHECKUP, null, values);
		db.close();

	}

	@Override
	public void edit(CheckUp checkUp) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.CU_DATEADDED,
				fmt.format(checkUp.getTimestamp()));
		values.put(DatabaseHandler.CU_PURPOSE, checkUp.getPurpose());
		values.put(DatabaseHandler.CU_DOCTORNAME, checkUp.getDoctorsName());
		values.put(DatabaseHandler.CU_NOTES, checkUp.getNotes());
		values.put(DatabaseHandler.CU_STATUS, checkUp.getStatus());

		try {
			if (checkUp.getImage() != null) {
				String encoded = checkUp.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				checkUp.getImage().setFileName(fileName);
				values.put(DatabaseHandler.CU_PHOTO, checkUp.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.CU_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (checkUp.getFacebookID() != null)
			values.put(DatabaseHandler.CU_FBPOSTID, checkUp.getFacebookID());

		db.update(DatabaseHandler.TABLE_CHECKUP, values, DatabaseHandler.CU_ID
				+ "=" + checkUp.getEntryID(), null);
		db.close();
	}

	@Override
	public ArrayList<CheckUp> getAll() throws DataAccessException {
		ArrayList<CheckUp> cuList = new ArrayList<CheckUp>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_CHECKUP;

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

				if (cursor.getString(6) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(6));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				}

				CheckUp cu = new CheckUp(cursor.getInt(0), cursor.getString(7),
						timestamp, cursor.getString(5), image,
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4));

				cuList.add(cu);
			} while (cursor.moveToNext());
		}

		db.close();
		return cuList;
	}

	@Override
	public void delete(CheckUp checkUp) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		if (checkUp.getImage() != null)
			ImageHandler.deleteImage(checkUp.getImage().getFileName());
		db.delete(DatabaseHandler.TABLE_CHECKUP, DatabaseHandler.CU_ID + "="
				+ checkUp.getEntryID(), null);
		db.close();
	}

	@Override
	public List<CheckUp> getAllReversed() throws DataAccessException {
		List<CheckUp> cuList = new ArrayList<CheckUp>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_CHECKUP
				+ " ORDER BY " + DatabaseHandler.CU_DATEADDED + " DESC";

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

				if (cursor.getString(6) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(6));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				}

				CheckUp cu = new CheckUp(cursor.getInt(0), cursor.getString(7),
						timestamp, cursor.getString(5), image,
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4));

				cuList.add(cu);
			} while (cursor.moveToNext());
		}

		db.close();
		return cuList;
	}

	@Override
	public CheckUp getLatest() throws DataAccessException {
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_CHECKUP
				+ " ORDER BY " + DatabaseHandler.CU_DATEADDED + " DESC LIMIT 1";

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

			if (cursor.getString(6) == null)
				image = null;
			else {
				image.setFileName(cursor.getString(6));
				Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
			}

			CheckUp cu = new CheckUp(cursor.getInt(0), cursor.getString(7),
					timestamp, cursor.getString(5), image, cursor.getString(2),
					cursor.getString(3), cursor.getString(4));
			return cu;
		}

		db.close();
		return null;
	}
}