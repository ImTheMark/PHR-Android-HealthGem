package com.example.phr.mobile.daoimpl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileFoodDao;
import com.example.phr.mobile.models.Food;
import com.google.common.collect.Lists;

public class MobileFoodDaoImpl implements MobileFoodDao {

	@Override
	public void add(Food food) {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

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

		db.insert(DatabaseHandler.TABLE_FOODLIST, null, values);
	}

	@Override
	public boolean exists(Food food) {
		Boolean bool = false;

		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_FOODLIST
				+ " WHERE " + DatabaseHandler.FOODLIST_ID + " = "
				+ food.getEntryID();

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst())
			bool = true;

		return bool;
	}

	@Override
	public List<Food> getAll() {
		List<Food> list = new ArrayList<Food>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_FOODLIST;

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
	public Food get(int id) {
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_FOODLIST
				+ " WHERE " + DatabaseHandler.FOODLIST_ID + " = " + id;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			Boolean bool = cursor.getInt(5) != 0;
			if (cursor.getString(4) == null) {
				Food food = new Food(cursor.getInt(0), cursor.getString(1),
						cursor.getDouble(2), cursor.getDouble(6),
						cursor.getDouble(7), cursor.getDouble(8),
						cursor.getString(3), null, bool);

				return food;
			} else {
				Food food = new Food(cursor.getInt(0), cursor.getString(1),
						cursor.getDouble(2), cursor.getDouble(6),
						cursor.getDouble(7), cursor.getDouble(8),
						cursor.getString(3), cursor.getInt(4), bool);
				return food;
			}

		}

		return null;
	}

	@Override
	public List<Food> getFoodListGivenRestaurantID(int id) {
		List<Food> list = new ArrayList<Food>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_FOODLIST
				+ " WHERE " + DatabaseHandler.FOODLIST_RESTAURANTID + " = "
				+ id;

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

		return list;
	}

	@Override
	public List<Food> getFoodListRecent() {
		List<Food> list = new ArrayList<Food>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_FOODLIST;

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

		Lists.reverse(list);
		int count = list.size();
		int limit = 10;
		List<Food> recentlist = new ArrayList<Food>();
		while (list.size() > 0 || limit > 0) {
			limit--;
			recentlist.add(list.remove(0));
		}

		return recentlist;
	}

}
