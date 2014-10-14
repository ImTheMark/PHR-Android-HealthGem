package com.example.phr.mobile.daoimpl;

import java.util.List;

import com.example.phr.mobile.dao.MobileFoodDao;
import com.example.phr.mobile.models.Food;

public class MobileFoodDaoImpl implements MobileFoodDao {

	@Override
	public int addReturnsEntryId(Food food) {
		// check if exists, return id if it does
		// add otherwise, return id
		return -1;
	}

	private boolean exists(Food food) {
		// check using id
		return true;
		// Old code
		/*
		 * Boolean bool = false; String selectQuery = "SELECT  * FROM " +
		 * DatabaseHandler.TABLE_FOODLIST + " WHERE " +
		 * DatabaseHandler.FOODLIST_ID + " = " + food.getEntryID();
		 * 
		 * Cursor cursor = db.rawQuery(selectQuery, null);
		 * 
		 * if (cursor.moveToFirst()) bool = true;
		 * 
		 * return bool;
		 */
	}

	@Override
	public List<Food> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Food get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	// OLD CODE
	/*
	 * @Override public ArrayList<Food> getAllFoodListEntry() throws
	 * DataAccessException { ArrayList<Food> foodList = new ArrayList<Food>();
	 * String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_FOODLIST;
	 * 
	 * SQLiteDatabase db = DatabaseHandler.getDBHandler()
	 * .getWritableDatabase(); Cursor cursor = db.rawQuery(selectQuery, null);
	 * 
	 * if (cursor.moveToFirst()) { do { Boolean bool = cursor.getInt(5) != 0;
	 * Food food = new Food(cursor.getString(0), cursor.getDouble(1),
	 * cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), bool);
	 * foodList.add(food); } while (cursor.moveToNext()); }
	 * 
	 * db.close(); return foodList; }
	 * 
	 * @Override public Food getFoodListEntry(SQLiteDatabase db, Integer
	 * foodListEntryID) throws DataAccessException { String selectQuery =
	 * "SELECT  * FROM " + DatabaseHandler.TABLE_FOODLIST + " WHERE " +
	 * DatabaseHandler.FOODLIST_ID + " = " + foodListEntryID;
	 * 
	 * Cursor cursor = db.rawQuery(selectQuery, null);
	 * 
	 * if (cursor.moveToFirst()) { Boolean bool = cursor.getInt(5) != 0; Food
	 * food = new Food(cursor.getString(0), cursor.getDouble(1),
	 * cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), bool); return
	 * food; }
	 * 
	 * return null; }
	 */

}
