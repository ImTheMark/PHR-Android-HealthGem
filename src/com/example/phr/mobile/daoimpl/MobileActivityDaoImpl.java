package com.example.phr.mobile.daoimpl;

import java.util.List;

import com.example.phr.mobile.dao.MobileActivityDao;
import com.example.phr.mobile.models.ActivitySingle;

public class MobileActivityDaoImpl implements MobileActivityDao {

	@Override
	public int addReturnsEntryId(ActivitySingle activity) {
		// check if activity exists using id, return id if it does
		// add otherwise, return id
		return -1;
	}

	private boolean exists(ActivitySingle activity) {
		return true;
	}

	@Override
	public List<ActivitySingle> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivitySingle get(int id) {
		// TODO Auto-generated method stub
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
	 * 
	 * @Override public ActivitySingle getActivityListEntry(SQLiteDatabase db,
	 * Integer activityID) throws DataAccessException { String selectQuery =
	 * "SELECT  * FROM " + DatabaseHandler.TABLE_ACTIVITYLIST + " WHERE " +
	 * DatabaseHandler.ACTLIST_ID + " = " + activityID;
	 * 
	 * Cursor cursor = db.rawQuery(selectQuery, null);
	 * 
	 * if (cursor.moveToFirst()) { ActivitySingle act = new
	 * ActivitySingle(cursor.getInt(0), cursor.getString(1),
	 * cursor.getDouble(2)); return act; } return null; }
	 */
}
