package com.example.phr.mobile.daoimpl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileBloodPressureDao;
import com.example.phr.mobile.models.MobileBloodPressure;

public class MobileBloodPressureDaoImpl implements MobileBloodPressureDao {

	@Override
	public void add(MobileBloodPressure bp) {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BP_ID, bp.getEntryID());
		values.put(DatabaseHandler.BP_DATEADDED, fmt.format(bp.getDateAdded()));
		values.put(DatabaseHandler.BP_SYSTOLIC, bp.getSystolic());
		values.put(DatabaseHandler.BP_DIASTOLIC, bp.getDiastolic());
		values.put(DatabaseHandler.BP_STATUS, bp.getStatus());
		if (bp.getEncodedImage() != null)
			values.put(DatabaseHandler.BP_PHOTO, bp.getEncodedImage());
		if (bp.getFbPost() != null)
			values.put(DatabaseHandler.BP_FBPOSTID, bp.getFbPost().getId());

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_BLOODPRESSURE, null, values);
		db.close(); // Closing database connection
	}

	@Override
	public List<MobileBloodPressure> getAllBloodPressure()
			throws ParseException {
		List<MobileBloodPressure> bpList = new ArrayList<MobileBloodPressure>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_BLOODPRESSURE;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				Date dateAdded = (Date) dateFormat.parse(cursor.getString(2));

				MobileBloodPressure bp = new MobileBloodPressure(
						cursor.getInt(1), dateAdded, cursor.getString(5),
						cursor.getString(6), cursor.getInt(3), cursor.getInt(4));

				bpList.add(bp);
			} while (cursor.moveToNext());
		}

		// return contact list
		db.close();
		return bpList;
	}

}
