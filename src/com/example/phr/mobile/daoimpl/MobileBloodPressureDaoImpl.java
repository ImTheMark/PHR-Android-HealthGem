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
import com.example.phr.exceptions.ImageHandlerException;
import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileBloodPressureDao;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileBloodPressureDaoImpl implements MobileBloodPressureDao {

	@Override
	public void add(BloodPressure bp) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BP_ID, bp.getEntryID());
		values.put(DatabaseHandler.BP_DATEADDED, fmt.format(bp.getTimestamp()));
		values.put(DatabaseHandler.BP_SYSTOLIC, bp.getSystolic());
		values.put(DatabaseHandler.BP_DIASTOLIC, bp.getDiastolic());
		values.put(DatabaseHandler.BP_STATUS, bp.getStatus());

		try {
			if (bp.getImage().getFileName() == null
					&& bp.getImage().getEncodedImage() != null) {
				String encoded = bp.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				bp.getImage().setFileName(fileName);
			}
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (bp.getImage().getFileName() != null)
			values.put(DatabaseHandler.BP_PHOTO, bp.getImage().getFileName());
		if (bp.getFbPost() != null)
			values.put(DatabaseHandler.BP_FBPOSTID, bp.getFbPost().getId());

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_BLOODPRESSURE, null, values);
		db.close(); // Closing database connection
	}

	@Override
	public List<BloodPressure> getAllBloodPressure() throws ParseException {
		List<BloodPressure> bpList = new ArrayList<BloodPressure>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_BLOODPRESSURE;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Timestamp timestamp = DateTimeParser.getTimestamp(cursor
						.getString(1));
				PHRImage image = new PHRImage();
				image.setFileName(cursor.getString(5));
				Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				String encoded = ImageHandler.encodeImageToBase64(bitmap);
				image.setEncodedImage(encoded);
				BloodPressure bp = new BloodPressure(cursor.getInt(0),
						timestamp, cursor.getString(4), image,
						cursor.getInt(2), cursor.getInt(3));

				bpList.add(bp);
			} while (cursor.moveToNext());
		}

		// return contact list
		db.close();
		return bpList;
	}
}
