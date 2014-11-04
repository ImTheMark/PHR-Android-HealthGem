package com.example.phr.mobile.daoimpl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phr.application.HealthGem;
import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.dao.MobileVerificationDao;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.Food;

public class MobileVerificationDaoImpl implements MobileVerificationDao {

	@Override
	public void addFoodList(List<Food> foodList) {
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
	public void addActivityList(List<Activity> activityList) {
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
	public List<Food> getFoodList() {
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
	public List<Activity> getActivityList() {
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
		return null;
	}

	@Override
	public void storeImage(String fileName) {
		HealthGem.getSharedPreferences().savePreferences(SPreference.VERIFICATION_TEMP_IMAGE, fileName);
	}

	@Override
	public String getImageFileName() {
		return HealthGem.getSharedPreferences().loadPreferences(SPreference.VERIFICATION_TEMP_IMAGE);
	}

}
