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
import com.example.phr.mobile.dao.MobileFoodDao;
import com.example.phr.mobile.dao.MobileFoodTrackerDao;
import com.example.phr.mobile.models.FBPost;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileFoodTrackerDaoImpl implements MobileFoodTrackerDao {

	MobileFoodDao mobileFoodDao = new MobileFoodDaoImpl();

	@Override
	public void add(FoodTrackerEntry food) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.FOOD_ID, food.getEntryID());
		values.put(DatabaseHandler.FOOD_DATEADDED,
				fmt.format(food.getTimestamp()));

		int foodEntryId = mobileFoodDao.addReturnsEntryId(food.getFood());

		values.put(DatabaseHandler.FOOD_FOODID, foodEntryId);
		values.put(DatabaseHandler.FOOD_SERVINGCOUNT, food.getServingCount());
		values.put(DatabaseHandler.FOOD_STATUS, food.getStatus());

		try {
			if (food.getImage() != null) {
				String encoded = food.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				food.getImage().setFileName(fileName);
				values.put(DatabaseHandler.FOOD_PHOTO, food.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.FOOD_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (food.getFbPost() != null)
			values.put(DatabaseHandler.FOOD_FBPOSTID, food.getFbPost().getId());

		db.insert(DatabaseHandler.TABLE_FOOD, null, values);
		db.close();

	}

	@Override
	public void edit(FoodTrackerEntry food) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.FOOD_ID, food.getEntryID());
		values.put(DatabaseHandler.FOOD_DATEADDED,
				fmt.format(food.getTimestamp()));

		int foodEntryId = mobileFoodDao.addReturnsEntryId(food.getFood());

		values.put(DatabaseHandler.FOOD_FOODID, foodEntryId);
		values.put(DatabaseHandler.FOOD_SERVINGCOUNT, food.getServingCount());
		values.put(DatabaseHandler.FOOD_STATUS, food.getStatus());

		try {
			if (food.getImage().getFileName() == null) {
				String encoded = food.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				food.getImage().setFileName(fileName);
				values.put(DatabaseHandler.FOOD_PHOTO, food.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.FOOD_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (food.getFbPost() != null)
			values.put(DatabaseHandler.FOOD_FBPOSTID, food.getFbPost().getId());

		db.update(DatabaseHandler.TABLE_FOOD, values, DatabaseHandler.FOOD_ID
				+ "=" + food.getEntryID(), null);
		db.close();
	}

	@Override
	public ArrayList<FoodTrackerEntry> getAll() throws DataAccessException {
		ArrayList<FoodTrackerEntry> foodList = new ArrayList<FoodTrackerEntry>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_FOOD;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				try {
					Timestamp timestamp = DateTimeParser.getTimestamp(cursor
							.getString(1));
					PHRImage image = new PHRImage();

					if (cursor.getString(5) == null)
						image = null;
					else {
						image.setFileName(cursor.getString(5));
						Bitmap bitmap = ImageHandler.loadImage(image
								.getFileName());
						String encoded = ImageHandler
								.encodeImageToBase64(bitmap);
						image.setEncodedImage(encoded);
					}

					FoodTrackerEntry foodTrackerEntry = new FoodTrackerEntry(
							cursor.getInt(0), new FBPost(cursor.getInt(6)),
							timestamp, cursor.getString(4), image,
							mobileFoodDao.get(cursor.getInt(2)),
							cursor.getDouble(3));

					foodList.add(foodTrackerEntry);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}

			} while (cursor.moveToNext());
		}

		db.close();
		return foodList;
	}

	@Override
	public List<FoodTrackerEntry> getAllReversed() throws DataAccessException {
		ArrayList<FoodTrackerEntry> foodList = new ArrayList<FoodTrackerEntry>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_FOOD
				+ " ORDER BY " + DatabaseHandler.FOOD_DATEADDED + " DESC";

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				try {
					Timestamp timestamp = DateTimeParser.getTimestamp(cursor
							.getString(1));
					PHRImage image = new PHRImage();

					if (cursor.getString(5) == null)
						image = null;
					else {
						image.setFileName(cursor.getString(5));
						Bitmap bitmap = ImageHandler.loadImage(image
								.getFileName());
						String encoded = ImageHandler
								.encodeImageToBase64(bitmap);
						image.setEncodedImage(encoded);
					}

					FoodTrackerEntry foodTrackerEntry = new FoodTrackerEntry(
							cursor.getInt(0), new FBPost(cursor.getInt(6)),
							timestamp, cursor.getString(4), image,
							mobileFoodDao.get(cursor.getInt(2)),
							cursor.getDouble(3));

					foodList.add(foodTrackerEntry);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}

			} while (cursor.moveToNext());
		}

		db.close();
		return foodList;
	}

	@Override
	public void delete(FoodTrackerEntry food) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_FOOD, DatabaseHandler.FOOD_ID + "="
				+ food.getEntryID(), null);
		db.close();
	}

	@Override
	public FoodTrackerEntry getLatest() {
		// TODO Auto-generated method stub
		return null;
	}

}