package com.example.phr.mobile.daoimpl;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.ImageHandlerException;
import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileFoodDao;
import com.example.phr.mobile.dao.MobileFoodTrackerDao;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.GroupedFood;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileFoodTrackerDaoImpl implements MobileFoodTrackerDao {

	MobileFoodDao mobileFoodDao = new MobileFoodDaoImpl();
	DecimalFormat df = new DecimalFormat("#.00");

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

		if (!mobileFoodDao.exists(food.getFood())) {
			mobileFoodDao.add(food.getFood());
		}

		values.put(DatabaseHandler.FOOD_FOODID, food.getFood().getEntryID());
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
		if (food.getFacebookID() != null)
			values.put(DatabaseHandler.FOOD_FBPOSTID, food.getFacebookID());

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

		if (!mobileFoodDao.exists(food.getFood())) {
			mobileFoodDao.add(food.getFood());
		}

		values.put(DatabaseHandler.FOOD_FOODID, food.getFood().getEntryID());
		values.put(DatabaseHandler.FOOD_SERVINGCOUNT, food.getServingCount());
		values.put(DatabaseHandler.FOOD_STATUS, food.getStatus());

		try {
			if (food.getImage().getFileName() != null) {
				String encoded = food.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				food.getImage().setFileName(fileName);
				values.put(DatabaseHandler.FOOD_PHOTO, food.getImage()
						.getFileName());
			} else {

				values.putNull(DatabaseHandler.FOOD_PHOTO);
				Log.e("fooddaoimpl", "image null");
			}
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (food.getFacebookID() != null)
			values.put(DatabaseHandler.FOOD_FBPOSTID, food.getFacebookID());

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
					}

					FoodTrackerEntry foodTrackerEntry = new FoodTrackerEntry(
							cursor.getInt(0), cursor.getString(6), timestamp,
							cursor.getString(4), image,
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
					}

					FoodTrackerEntry foodTrackerEntry = new FoodTrackerEntry(
							cursor.getInt(0), cursor.getString(6), timestamp,
							cursor.getString(4), image,
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

		if (food.getImage() != null)
			ImageHandler.deleteImage(food.getImage().getFileName());
		db.delete(DatabaseHandler.TABLE_FOOD, DatabaseHandler.FOOD_ID + "="
				+ food.getEntryID(), null);
		db.close();
	}

	@Override
	public FoodTrackerEntry getLatest() throws DataAccessException {
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_FOOD
				+ " ORDER BY " + DatabaseHandler.FOOD_DATEADDED
				+ " DESC LIMIT 1";

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			try {
				Timestamp timestamp = DateTimeParser.getTimestamp(cursor
						.getString(1));
				PHRImage image = new PHRImage();

				if (cursor.getString(5) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(5));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				}

				FoodTrackerEntry foodTrackerEntry = new FoodTrackerEntry(
						cursor.getInt(0), cursor.getString(6), timestamp,
						cursor.getString(4), image, mobileFoodDao.get(cursor
								.getInt(2)), cursor.getDouble(3));
				return foodTrackerEntry;
			} catch (ParseException e) {
				throw new DataAccessException(
						"Cannot complete operation due to parse failure", e);
			}
		}

		db.close();
		return null;
	}

	@Override
	public List<List<FoodTrackerEntry>> getAllGroupedByDate()
			throws DataAccessException {
		List<List<FoodTrackerEntry>> groupedFoodList = new ArrayList<List<FoodTrackerEntry>>();
		List<FoodTrackerEntry> foodList = new ArrayList<FoodTrackerEntry>();
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
					}

					FoodTrackerEntry foodTrackerEntry = new FoodTrackerEntry(
							cursor.getInt(0), cursor.getString(6), timestamp,
							cursor.getString(4), image,
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

		while (foodList.size() != 0) {
			List<FoodTrackerEntry> foodListPerDay = new ArrayList<FoodTrackerEntry>();
			String monthDay = DateTimeParser.getMonthDay(foodList.get(0)
					.getTimestamp());

			do {
				FoodTrackerEntry f = foodList.remove(0);
				foodListPerDay.add(f);
			} while (foodList.size() != 0
					&& monthDay.equals(DateTimeParser.getMonthDay(foodList.get(
							0).getTimestamp())));

			groupedFoodList.add(foodListPerDay);
		}

		return groupedFoodList;
	}

	@Override
	public List<FoodTrackerEntry> getAllFromDate(Timestamp date)
			throws DataAccessException {
		List<FoodTrackerEntry> foodList = new ArrayList<FoodTrackerEntry>();
		List<FoodTrackerEntry> foodListFromDate = new ArrayList<FoodTrackerEntry>();
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
					}

					FoodTrackerEntry foodTrackerEntry = new FoodTrackerEntry(
							cursor.getInt(0), cursor.getString(6), timestamp,
							cursor.getString(4), image,
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

		Boolean dateHasPassedFromGivenDate = false;

		/*
		 * while (foodList.size() != 0 && !dateHasPassedFromGivenDate) {
		 * FoodTrackerEntry food = foodList.remove(0); // if
		 * (foodList.get(0).getTimestamp().equals(date)) if
		 * (DateTimeParser.getMonthDay(food.getTimestamp()).equals(
		 * DateTimeParser.getMonthDay(date))) foodListFromDate.add(food); else
		 * if (food.getTimestamp().after(date)) dateHasPassedFromGivenDate =
		 * true; }
		 */

		while (foodList.size() != 0) {
			FoodTrackerEntry food = foodList.remove(0);
			if (DateTimeParser.getMonthDay(food.getTimestamp()).equals(
					DateTimeParser.getMonthDay(date)))
				foodListFromDate.add(food);
		}
		return foodListFromDate;
	}

	@Override
	public GroupedFood getFromDateCalculated(Timestamp date)
			throws DataAccessException {
		List<FoodTrackerEntry> foodList = new ArrayList<FoodTrackerEntry>();
		List<FoodTrackerEntry> foodListFromDate = new ArrayList<FoodTrackerEntry>();
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
					}

					FoodTrackerEntry foodTrackerEntry = new FoodTrackerEntry(
							cursor.getInt(0), cursor.getString(6), timestamp,
							cursor.getString(4), image,
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

		Boolean dateHasPassedFromGivenDate = false;
		double groupedProtein = 0;
		double groupedCalorie = 0;
		double groupedCarb = 0;
		double groupedFat = 0;
		Timestamp groupedDate = null;

		Log.e("foodlistsize", String.valueOf(foodList.size()));

		while (foodList.size() != 0) {
			FoodTrackerEntry food = foodList.remove(0);

			if (DateTimeParser.getMonthDay(food.getTimestamp()).equals(
					DateTimeParser.getMonthDay(date))) {
				foodListFromDate.add(food);
				groupedProtein += food.getFood().getProtein()
						* food.getServingCount();
				groupedCalorie += food.getFood().getCalorie()
						* food.getServingCount();
				groupedCarb += food.getFood().getCarbohydrate()
						* food.getServingCount();
				groupedFat += food.getFood().getFat() * food.getServingCount();
				groupedDate = food.getTimestamp();
			}
		}
		GroupedFood groupedFood = new GroupedFood(groupedDate,
				Double.parseDouble(df.format(groupedCalorie)),
				Double.parseDouble(df.format(groupedProtein)),
				Double.parseDouble(df.format(groupedFat)),
				Double.parseDouble(df.format(groupedCarb)));
		return groupedFood;
	}

	@Override
	public List<GroupedFood> getAllGroupedByDateCalculated()
			throws DataAccessException {
		Log.e("getAllGroupedDateCalculated", "called");
		List<GroupedFood> calGroupedFood = new ArrayList<GroupedFood>();
		List<FoodTrackerEntry> foodList = new ArrayList<FoodTrackerEntry>();
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
					}

					FoodTrackerEntry foodTrackerEntry = new FoodTrackerEntry(
							cursor.getInt(0), cursor.getString(6), timestamp,
							cursor.getString(4), image,
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
		FoodTrackerEntry f;
		while (foodList.size() != 0) {
			List<FoodTrackerEntry> foodListPerDay = new ArrayList<FoodTrackerEntry>();
			String monthDay = DateTimeParser.getMonthDay(foodList.get(0)
					.getTimestamp());

			double groupedProtein = 0;
			double groupedCalorie = 0;
			double groupedCarb = 0;
			double groupedFat = 0;
			f = foodList.get(0);
			Timestamp date = null;
			while (foodList.size() != 0
					&& monthDay.equals(DateTimeParser.getMonthDay(f
							.getTimestamp())))

			{
				Log.e("same group",
						DateTimeParser.getMonthDay(f.getTimestamp()));
				date = f.getTimestamp();
				groupedProtein += f.getFood().getProtein()
						* f.getServingCount();
				groupedCalorie += f.getFood().getCalorie()
						* f.getServingCount();
				groupedCarb += f.getFood().getCarbohydrate()
						* f.getServingCount();
				groupedFat += f.getFood().getFat() * f.getServingCount();
				foodListPerDay.add(f);
				Log.e("foodlistsize", String.valueOf(foodList.size()));
				foodList.remove(0);
				if (foodList.size() != 0)
					f = foodList.get(0);
			}
			Log.e("date", String.valueOf(date));
			Log.e("grouped cal", String.valueOf(groupedCalorie));
			calGroupedFood.add(new GroupedFood(date, Double.parseDouble(df
					.format(groupedCalorie)), Double.parseDouble(df
					.format(groupedProtein)), Double.parseDouble(df
					.format(groupedFat)), Double.parseDouble(df
					.format(groupedCarb))));

		}

		return calGroupedFood;
	}
}
