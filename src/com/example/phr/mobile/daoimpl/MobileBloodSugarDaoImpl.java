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
import com.example.phr.mobile.dao.MobileBloodSugarDao;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.FBPost;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileBloodSugarDaoImpl implements MobileBloodSugarDao {

	@Override
	public void add(BloodSugar bloodSugar) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BS_ID, bloodSugar.getEntryID());
		values.put(DatabaseHandler.BS_DATEADDED, fmt.format(bloodSugar.getTimestamp()));
		values.put(DatabaseHandler.BS_BLOODSUGAR, bloodSugar.getBloodSugar());
		values.put(DatabaseHandler.BS_TYPE, bloodSugar.getType());
		values.put(DatabaseHandler.BS_STATUS, bloodSugar.getStatus());

		try {
			if (bloodSugar.getImage().getFileName() == null
					&& bloodSugar.getImage().getEncodedImage() != null) {
				String encoded = bloodSugar.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				bloodSugar.getImage().setFileName(fileName);
			}
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (bloodSugar.getImage().getFileName() != null)
			values.put(DatabaseHandler.BS_PHOTO, bloodSugar.getImage().getFileName());
		if (bloodSugar.getFbPost() != null)
			values.put(DatabaseHandler.BS_FBPOSTID, bloodSugar.getFbPost().getId());

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_BLOODSUGAR, null, values);
		db.close(); // Closing database connection
	}

	@Override
	public ArrayList<BloodSugar> getAll() throws ParseException {
		ArrayList<BloodSugar> bsList = new ArrayList<BloodSugar>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_BLOODSUGAR;

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
				
				BloodSugar bs = new BloodSugar(cursor.getInt(0),
						new FBPost(cursor.getInt(6)),
						timestamp, 
						cursor.getString(4), 
						image,
						cursor.getDouble(2), 
						cursor.getString(3));

				bsList.add(bs);
			} while (cursor.moveToNext());
		}

		// return contact list
		db.close();
		return bsList;
	}
	
}