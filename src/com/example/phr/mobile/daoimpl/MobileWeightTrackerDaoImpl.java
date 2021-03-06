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
import com.example.phr.mobile.dao.MobileWeightTrackerDao;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.Weight;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileWeightTrackerDaoImpl implements MobileWeightTrackerDao {

	@Override
	public void add(Weight weight) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.WEIGHT_ID, weight.getEntryID());
		values.put(DatabaseHandler.WEIGHT_DATEADDED,
				fmt.format(weight.getTimestamp()));
		values.put(DatabaseHandler.WEIGHT_POUNDS, weight.getWeightInPounds());
		values.put(DatabaseHandler.WEIGHT_STATUS, weight.getStatus());

		try {
			if (weight.getImage() != null) {
				String encoded = weight.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				weight.getImage().setFileName(fileName);
				values.put(DatabaseHandler.WEIGHT_PHOTO, weight.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.WEIGHT_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (weight.getFacebookID() != null)
			values.put(DatabaseHandler.WEIGHT_FBPOSTID, weight.getFacebookID());

		db.insert(DatabaseHandler.TABLE_WEIGHT, null, values);
		db.close();
	}

	@Override
	public void edit(Weight weight) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.WEIGHT_DATEADDED,
				fmt.format(weight.getTimestamp()));
		values.put(DatabaseHandler.WEIGHT_POUNDS, weight.getWeightInPounds());
		values.put(DatabaseHandler.WEIGHT_STATUS, weight.getStatus());

		try {
			if (weight.getImage().getFileName() == null) {
				String encoded = weight.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				weight.getImage().setFileName(fileName);
				values.put(DatabaseHandler.WEIGHT_PHOTO, weight.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.WEIGHT_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (weight.getFacebookID() != null)
			values.put(DatabaseHandler.WEIGHT_FBPOSTID, weight.getFacebookID());

		db.update(DatabaseHandler.TABLE_WEIGHT, values,
				DatabaseHandler.WEIGHT_ID + "=" + weight.getEntryID(), null);
		db.close();
	}

	@Override
	public ArrayList<Weight> getAll() throws DataAccessException {
		ArrayList<Weight> weightList = new ArrayList<Weight>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_WEIGHT;

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

				if (cursor.getString(4) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(4));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
					String encoded = ImageHandler.encodeImageToBase64(bitmap);
					image.setEncodedImage(encoded);
				}
				Weight weight = new Weight(cursor.getInt(0),
						cursor.getString(5), timestamp, cursor.getString(3),
						image, cursor.getDouble(2));

				weightList.add(weight);
			} while (cursor.moveToNext());
		}

		db.close();
		return weightList;
	}

	@Override
	public void delete(Weight weight) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		if (weight.getImage() != null)
			ImageHandler.deleteImage(weight.getImage().getFileName());
		db.delete(DatabaseHandler.TABLE_WEIGHT, DatabaseHandler.WEIGHT_ID + "="
				+ weight.getEntryID(), null);
		db.close();
	}

	@Override
	public List<Weight> getAllReversed() throws DataAccessException {
		List<Weight> weightList = new ArrayList<Weight>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_WEIGHT
				+ " ORDER BY " + DatabaseHandler.WEIGHT_DATEADDED + " DESC";

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

				if (cursor.getString(4) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(4));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
					String encoded = ImageHandler.encodeImageToBase64(bitmap);
					image.setEncodedImage(encoded);
				}

				Weight weight = new Weight(cursor.getInt(0),
						cursor.getString(5), timestamp, cursor.getString(3),
						image, cursor.getDouble(2));

				weightList.add(weight);
			} while (cursor.moveToNext());
		}

		db.close();
		return weightList;
	}

	@Override
	public List<List<Weight>> getAllGroupedByDate() throws DataAccessException {
		List<List<Weight>> groupedWeightList = new ArrayList<List<Weight>>();

		List<Weight> weightList = new ArrayList<Weight>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_WEIGHT
				+ " ORDER BY " + DatabaseHandler.WEIGHT_DATEADDED + " DESC";

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

				if (cursor.getString(4) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(4));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
					String encoded = ImageHandler.encodeImageToBase64(bitmap);
					image.setEncodedImage(encoded);
				}

				Weight weight = new Weight(cursor.getInt(0),
						cursor.getString(5), timestamp, cursor.getString(3),
						image, cursor.getDouble(2));

				weightList.add(weight);
			} while (cursor.moveToNext());
		}
		db.close();

		while (weightList.size() != 0) {
			List<Weight> weightListPerDay = new ArrayList<Weight>();
			String monthDay = DateTimeParser.getMonthDay(weightList.get(0)
					.getTimestamp());

			do {
				Weight weight = weightList.remove(0);
				weightListPerDay.add(weight);
			} while (weightList.size() != 0
					&& monthDay.equals(DateTimeParser.getMonthDay(weightList
							.get(0).getTimestamp())));

			groupedWeightList.add(weightListPerDay);
		}

		return groupedWeightList;
	}

	@Override
	public Weight getLatest() throws DataAccessException {
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_WEIGHT
				+ " ORDER BY " + DatabaseHandler.WEIGHT_DATEADDED
				+ " DESC LIMIT 1";

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

			if (cursor.getString(4) == null)
				image = null;
			else {
				image.setFileName(cursor.getString(4));
				Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				String encoded = ImageHandler.encodeImageToBase64(bitmap);
				image.setEncodedImage(encoded);
			}

			Weight weight = new Weight(cursor.getInt(0), cursor.getString(5),
					timestamp, cursor.getString(3), image, cursor.getDouble(2));
			return weight;
		}
		db.close();
		return null;
	}

	@Override
	public List<Weight> getLastTwoEntries() throws DataAccessException {
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_WEIGHT
				+ " ORDER BY " + DatabaseHandler.WEIGHT_DATEADDED
				+ " DESC LIMIT 2";
		List<Weight> list = new ArrayList<Weight>();

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

				if (cursor.getString(4) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(4));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
					String encoded = ImageHandler.encodeImageToBase64(bitmap);
					image.setEncodedImage(encoded);
				}

				Weight weight = new Weight(cursor.getInt(0),
						cursor.getString(5), timestamp, cursor.getString(3),
						image, cursor.getDouble(2));
				list.add(weight);
			} while (cursor.moveToNext());
		}
		db.close();
		return list;
	}
}