package com.example.phr.mobile.daoimpl;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.ImageHandlerException;
import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.dao.MobileVerificationDao;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.Restaurant;
import com.example.phr.mobile.models.SportEstablishment;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileVerificationDaoImpl implements MobileVerificationDao {

	@Override
	public void addFoodListToTempDB(List<Food> foodList) {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		db.delete(DatabaseHandler.TABLE_TEMP_FOODLIST, null, null);

		for (Food food : foodList) {
			ContentValues values = new ContentValues();
			values.put(DatabaseHandler.FOODLIST_ID, food.getEntryID());
			values.put(DatabaseHandler.FOODLIST_NAME, food.getName());
			values.put(DatabaseHandler.FOODLIST_CALORIE, food.getCalorie());
			values.put(DatabaseHandler.FOODLIST_SERVING, food.getServing());
			values.put(DatabaseHandler.FOODLIST_RESTAURANTID,
					food.getRestaurantID());
			values.put(DatabaseHandler.FOODLIST_FROMFATSECRET,
					food.getFromFatsecret());
			values.put(DatabaseHandler.FOODLIST_PROTEIN, food.getProtein());
			values.put(DatabaseHandler.FOODLIST_FAT, food.getFat());
			values.put(DatabaseHandler.FOODLIST_CARBOHYDRATE,
					food.getCarbohydrate());
			db.insert(DatabaseHandler.TABLE_TEMP_FOODLIST, null, values);
		}

		db.close();

	}

	@Override
	public void addActivityListToTempDB(List<Activity> activityList) {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		db.delete(DatabaseHandler.TABLE_TEMP_ACTIVITYLIST, null, null);

		for (Activity act : activityList) {
			ContentValues values = new ContentValues();
			values.put(DatabaseHandler.ACTLIST_ID, act.getEntryID());
			values.put(DatabaseHandler.ACTLIST_NAME, act.getName());
			values.put(DatabaseHandler.ACTLIST_MET, act.getMET());

			db.insert(DatabaseHandler.TABLE_TEMP_ACTIVITYLIST, null, values);
		}

		db.close();
	}

	@Override
	public List<Food> getFoodListFromTempDB() {
		List<Food> list = new ArrayList<Food>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_TEMP_FOODLIST;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Boolean bool = cursor.getInt(5) != 0;
				Food food = new Food(cursor.getInt(0), cursor.getString(1),
						cursor.getDouble(2), cursor.getDouble(6),
						cursor.getDouble(7), cursor.getDouble(8),
						cursor.getString(3), cursor.getInt(4), bool);
				list.add(food);
			} while (cursor.moveToNext());
		}

		db.close();
		return list;
	}

	@Override
	public List<Activity> getActivityListFromTempDB() {
		List<Activity> list = new ArrayList<Activity>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_TEMP_ACTIVITYLIST;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Activity act = new Activity(cursor.getInt(0),
						cursor.getString(1), cursor.getDouble(2));
				list.add(act);
			} while (cursor.moveToNext());
		}

		db.close();
		return list;
	}

	@Override
	public void storeEncodedImage(String encodedImage) {
		HealthGem.getSharedPreferences().savePreferences(
				SPreference.VERIFICATION_TEMP_IMAGE,
				ImageHandler.saveImageReturnFileName(encodedImage));
	}

	@Override
	public String getImageFileName() {
		return HealthGem.getSharedPreferences().loadPreferences(
				SPreference.VERIFICATION_TEMP_IMAGE);
	}

	@Override
	public int getUnverifiedPostsCount() {
		return getAllUnverifiedPosts().size();
	}

	@Override
	public void addUnverifiedFoodPost(UnverifiedFoodEntry entry) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.UNVERIFIED_FOOD_ID, entry.getEntryID());
		values.put(DatabaseHandler.UNVERIFIED_FOOD_DATEADDED, fmt.format(entry.getTimestamp()));
		values.put(DatabaseHandler.UNVERIFIED_FOOD_EXTRACTEDWORD, entry.getExtractedWord());
		values.put(DatabaseHandler.UNVERIFIED_FOOD_FOODID, entry.getFood().getEntryID());
		values.put(DatabaseHandler.UNVERIFIED_FOOD_SERVINGCOUNT, entry.getServingCount());
		values.put(DatabaseHandler.UNVERIFIED_FOOD_STATUS, entry.getStatus());

		try {
			if (entry.getImage() != null) {
				String encoded = entry.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				entry.getImage().setFileName(fileName);
				values.put(DatabaseHandler.UNVERIFIED_FOOD_PHOTO, entry.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.UNVERIFIED_FOOD_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (entry.getFacebookID() != null)
			values.put(DatabaseHandler.UNVERIFIED_FOOD_FBPOSTID, entry.getFacebookID());

		db.insert(DatabaseHandler.TABLE_UNVERIFIED_FOOD, null, values);
		db.close();
	}

	@Override
	public void addUnverifiedRestaurantPost(UnverifiedRestaurantEntry entry) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.UNVERIFIED_RESTO_ID, entry.getEntryID());
		values.put(DatabaseHandler.UNVERIFIED_RESTO_DATEADDED, fmt.format(entry.getTimestamp()));
		values.put(DatabaseHandler.UNVERIFIED_RESTO_EXTRACTEDWORD, entry.getExtractedWord());
		values.put(DatabaseHandler.UNVERIFIED_RESTO_RESTOID, entry.getRestaurant().getEntryID());
		values.put(DatabaseHandler.UNVERIFIED_RESTO_STATUS, entry.getStatus());

		try {
			if (entry.getImage() != null) {
				String encoded = entry.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				entry.getImage().setFileName(fileName);
				values.put(DatabaseHandler.UNVERIFIED_RESTO_PHOTO, entry.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.UNVERIFIED_RESTO_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (entry.getFacebookID() != null)
			values.put(DatabaseHandler.UNVERIFIED_RESTO_FBPOSTID, entry.getFacebookID());

		db.insert(DatabaseHandler.TABLE_UNVERIFIED_RESTO, null, values);
		db.close();
	}

	@Override
	public void addUnverifiedActivityPost(UnverifiedActivityEntry entry) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.UNVERIFIED_ACTIVITY_ID, entry.getEntryID());
		values.put(DatabaseHandler.UNVERIFIED_ACTIVITY_DATEADDED, fmt.format(entry.getTimestamp()));
		values.put(DatabaseHandler.UNVERIFIED_ACTIVITY_EXTRACTEDWORD, entry.getExtractedWord());
		values.put(DatabaseHandler.UNVERIFIED_ACTIVITY_ACTIVITYID, entry.getActivity().getEntryID());
		values.put(DatabaseHandler.UNVERIFIED_ACTIVITY_DURATIONINSECONDS, entry.getDurationInSeconds());
		values.put(DatabaseHandler.UNVERIFIED_ACTIVITY_CALORIEBURNED, entry.getCalorieBurnedPerHour());
		values.put(DatabaseHandler.UNVERIFIED_ACTIVITY_STATUS, entry.getStatus());

		try {
			if (entry.getImage() != null) {
				String encoded = entry.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				entry.getImage().setFileName(fileName);
				values.put(DatabaseHandler.UNVERIFIED_ACTIVITY_PHOTO, entry.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.UNVERIFIED_ACTIVITY_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (entry.getFacebookID() != null)
			values.put(DatabaseHandler.UNVERIFIED_ACTIVITY_FBPOSTID, entry.getFacebookID());

		db.insert(DatabaseHandler.TABLE_UNVERIFIED_ACTIVITY, null, values);
		db.close();
	}

	@Override
	public void addUnverifiedSportEstablishmentPost(
			UnverifiedSportsEstablishmentEntry entry) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.UNVERIFIED_SPORTEST_ID, entry.getEntryID());
		values.put(DatabaseHandler.UNVERIFIED_SPORTEST_DATEADDED, fmt.format(entry.getTimestamp()));
		values.put(DatabaseHandler.UNVERIFIED_SPORTEST_EXTRACTEDWORD, entry.getExtractedWord());
		values.put(DatabaseHandler.UNVERIFIED_SPORTEST_GYMID, entry.getSportEstablishment().getEntryID());
		values.put(DatabaseHandler.UNVERIFIED_SPORTEST_STATUS, entry.getStatus());

		try {
			if (entry.getImage() != null) {
				String encoded = entry.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				entry.getImage().setFileName(fileName);
				values.put(DatabaseHandler.UNVERIFIED_SPORTEST_PHOTO, entry.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.UNVERIFIED_SPORTEST_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (entry.getFacebookID() != null)
			values.put(DatabaseHandler.UNVERIFIED_SPORTEST_FBPOSTID, entry.getFacebookID());

		db.insert(DatabaseHandler.TABLE_UNVERIFIED_SPORTEST, null, values);
		db.close();
	}

	@Override
	public List<TrackerEntry> getAllUnverifiedPosts(){
		List<TrackerEntry> list = new ArrayList<TrackerEntry>();
		
		try {
			list.addAll(getAllUnverifiedActivityPosts());
			list.addAll(getAllUnverifiedFoodPosts());
			list.addAll(getAllUnverifiedRestaurantPosts());
			list.addAll(getAllUnverifiedSportsEstablishmentPosts());

			Collections.sort(list, new Comparator<TrackerEntry>() {
				@Override
				public int compare(TrackerEntry e1, TrackerEntry e2) {
					return e1.getTimestamp().compareTo(e2.getTimestamp());
				}
			});

			Collections.reverse(list);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void deleteUnverifiedFoodPost(UnverifiedFoodEntry entry) {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		if (entry.getImage() != null)
			ImageHandler.deleteImage(entry.getImage().getFileName());
		db.delete(DatabaseHandler.TABLE_UNVERIFIED_FOOD, DatabaseHandler.UNVERIFIED_FOOD_ID + "="
				+ entry.getEntryID(), null);
		db.close();
	}

	@Override
	public void deleteUnverifiedRestaurantPost(UnverifiedRestaurantEntry entry) {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		if (entry.getImage() != null)
			ImageHandler.deleteImage(entry.getImage().getFileName());
		db.delete(DatabaseHandler.TABLE_UNVERIFIED_RESTO, DatabaseHandler.UNVERIFIED_RESTO_ID + "="
				+ entry.getEntryID(), null);
		db.close();
	}

	@Override
	public void deleteUnverifiedActivityPost(UnverifiedActivityEntry entry) {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		if (entry.getImage() != null)
			ImageHandler.deleteImage(entry.getImage().getFileName());
		db.delete(DatabaseHandler.TABLE_UNVERIFIED_ACTIVITY, DatabaseHandler.UNVERIFIED_ACTIVITY_ID + "="
				+ entry.getEntryID(), null);
		db.close();
	}

	@Override
	public void deleteUnverifiedSportEstablishmentPost(
			UnverifiedSportsEstablishmentEntry entry) {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		if (entry.getImage() != null)
			ImageHandler.deleteImage(entry.getImage().getFileName());
		db.delete(DatabaseHandler.TABLE_UNVERIFIED_SPORTEST, DatabaseHandler.UNVERIFIED_SPORTEST_ID + "="
				+ entry.getEntryID(), null);
		db.close();
	}

	@Override
	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts() throws DataAccessException {
		List<UnverifiedFoodEntry> list = new ArrayList<UnverifiedFoodEntry>();
		

		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_UNVERIFIED_FOOD;

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

					UnverifiedFoodEntry entry = new UnverifiedFoodEntry(cursor.getInt(0), cursor.getString(7),
							timestamp, cursor.getString(5), image, new Food(cursor.getInt(3)),
							cursor.getDouble(4), cursor.getString(2));

					list.add(entry);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}

			} while (cursor.moveToNext());
		}

		db.close();
		
		return list;
	}

	@Override
	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts() throws DataAccessException {
		List<UnverifiedActivityEntry> list = new ArrayList<UnverifiedActivityEntry>();
		

		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_UNVERIFIED_ACTIVITY;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				try {
					Timestamp timestamp = DateTimeParser.getTimestamp(cursor
							.getString(1));
					PHRImage image = new PHRImage();

					if (cursor.getString(7) == null)
						image = null;
					else {
						image.setFileName(cursor.getString(7));
						Bitmap bitmap = ImageHandler.loadImage(image
								.getFileName());
					}

					UnverifiedActivityEntry entry = new UnverifiedActivityEntry(cursor.getInt(0), cursor.getString(8),
							timestamp, cursor.getString(6), image,
							new Activity(cursor.getInt(3)), cursor.getInt(4),
							cursor.getDouble(5), cursor.getString(2));

					list.add(entry);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}

			} while (cursor.moveToNext());
		}

		db.close();
		
		return list;
	}

	@Override
	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts() throws DataAccessException {
		List<UnverifiedRestaurantEntry> list = new ArrayList<UnverifiedRestaurantEntry>();

		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_UNVERIFIED_RESTO;

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

					UnverifiedRestaurantEntry entry = new UnverifiedRestaurantEntry(
							cursor.getInt(0), cursor.getString(6),
							timestamp, cursor.getString(4), image,
							cursor.getString(2), new Restaurant(cursor.getInt(3)), null);

					list.add(entry);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}

			} while (cursor.moveToNext());
		}

		db.close();
		
		return list;
	}

	@Override
	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts() throws DataAccessException {
		List<UnverifiedSportsEstablishmentEntry> list = new ArrayList<UnverifiedSportsEstablishmentEntry>();

		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_UNVERIFIED_SPORTEST;

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

					UnverifiedSportsEstablishmentEntry entry = new UnverifiedSportsEstablishmentEntry(
							cursor.getInt(0),
							cursor.getString(6), timestamp, cursor.getString(4),
							image, cursor.getString(2),
							new SportEstablishment(cursor.getInt(3)), null);

					list.add(entry);

				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}

			} while (cursor.moveToNext());
		}

		db.close();
		
		return list;
	}

}
