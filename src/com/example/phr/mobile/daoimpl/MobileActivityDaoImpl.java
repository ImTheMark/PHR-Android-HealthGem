package com.example.phr.mobile.daoimpl;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileActivityDao;
import com.example.phr.mobile.models.Activity;

public class MobileActivityDaoImpl implements MobileActivityDao {

	@Override
	public void addReturnsEntryId(Activity activity) {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		
		if(!exists(activity)){
			ContentValues values = new ContentValues();
			values.put(DatabaseHandler.ACTLIST_ID, activity.getEntryID());
			values.put(DatabaseHandler.ACTLIST_NAME, activity.getName());
			values.put(DatabaseHandler.ACTLIST_MET, activity.getMET());
			
			db.insert(DatabaseHandler.TABLE_ACTIVITYLIST, null, values);
		}
	}

	private boolean exists(Activity activity) {
		boolean bool = false;
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_ACTIVITYLIST + " WHERE " + DatabaseHandler.ACTLIST_ID + " = " + activity.getEntryID();

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) 
			bool = true;
		
		return bool;
	}

	@Override
	public List<Activity> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity get(int activityID) {
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_ACTIVITYLIST + " WHERE " +
				DatabaseHandler.ACTLIST_ID + " = " + activityID;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) { 
			Activity act = new Activity(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)); 
			return act; 
		} 
		return null;
	}

	// OLD CODE
	/*
	 * @Override public void addActivityListEntry(SQLiteDatabase db,
	 * ActivitySingle activity) { ContentValues values = new ContentValues();
	 * values.put(DatabaseHandler.ACTLIST_ID, activity.getEntryID());
	 * values.put(DatabaseHandler.ACTLIST_NAME, activity.getName());
	 * values.put(DatabaseHandler.ACTLIST_MET, activity.getMET());
	 * db.insert(DatabaseHandler.TABLE_ACTIVITYLIST, null, values); }
	 * 
	 * @Override public ArrayList<ActivitySingle> getAllActivityListEntry()
	 * throws DataAccessException { ArrayList<ActivitySingle> actList = new
	 * ArrayList<ActivitySingle>(); String selectQuery = "SELECT  * FROM " +
	 * DatabaseHandler.TABLE_ACTIVITYLIST;
	 * 
	 * SQLiteDatabase db = DatabaseHandler.getDBHandler()
	 * .getWritableDatabase(); Cursor cursor = db.rawQuery(selectQuery, null);
	 * 
	 * if (cursor.moveToFirst()) { do { actList.add(new
	 * ActivitySingle(cursor.getInt(0), cursor .getString(2),
	 * cursor.getDouble(3))); } while (cursor.moveToNext()); }
	 * 
	 * db.close(); return actList; }
	 * 
	 * @Override public Boolean activityListEntryExists(SQLiteDatabase db,
	 * Integer activityID) throws DataAccessException { Boolean bool = false;
	 * String selectQuery = "SELECT  * FROM " +
	 * DatabaseHandler.TABLE_ACTIVITYLIST + " WHERE " +
	 * DatabaseHandler.ACTLIST_ID + " = " + activityID;
	 * 
	 * Cursor cursor = db.rawQuery(selectQuery, null);
	 * 
	 * if (cursor.moveToFirst()) bool = true;
	 * 
	 * return bool; }
	 */
}
