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
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.ImageHandlerException;
import com.example.phr.local_db.DatabaseHandler;
import com.example.phr.mobile.dao.MobileCheckupDao;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.mobile.models.FBPost;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileCheckupDaoImpl implements MobileCheckupDao {

	@Override
	public void add(CheckUp checkUp) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.CU_ID, checkUp.getEntryID());
		values.put(DatabaseHandler.CU_DATEADDED, fmt.format(checkUp.getTimestamp()));
		values.put(DatabaseHandler.CU_PURPOSE, checkUp.getPurpose());
		values.put(DatabaseHandler.CU_DOCTORNAME, checkUp.getDoctorsName());
		values.put(DatabaseHandler.CU_NOTES, checkUp.getNotes());
		values.put(DatabaseHandler.CU_STATUS, checkUp.getStatus());

		try {
			if (checkUp.getImage().getFileName() == null
					&& checkUp.getImage().getEncodedImage() != null) {
				String encoded = checkUp.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				checkUp.getImage().setFileName(fileName);
			}
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (checkUp.getImage().getFileName() != null)
			values.put(DatabaseHandler.CU_PHOTO, checkUp.getImage().getFileName());
		if (checkUp.getFbPost() != null)
			values.put(DatabaseHandler.CU_FBPOSTID, checkUp.getFbPost().getId());

		db.insert(DatabaseHandler.TABLE_CHECKUP, null, values);
		db.close();
		
	}

	@Override
	public void edit(CheckUp checkUp) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.CU_DATEADDED, fmt.format(checkUp.getTimestamp()));
		values.put(DatabaseHandler.CU_PURPOSE, checkUp.getPurpose());
		values.put(DatabaseHandler.CU_DOCTORNAME, checkUp.getDoctorsName());
		values.put(DatabaseHandler.CU_NOTES, checkUp.getNotes());
		values.put(DatabaseHandler.CU_STATUS, checkUp.getStatus());

		try {
			if (checkUp.getImage().getFileName() == null
					&& checkUp.getImage().getEncodedImage() != null) {
				String encoded = checkUp.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				checkUp.getImage().setFileName(fileName);
			}
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (checkUp.getImage().getFileName() != null)
			values.put(DatabaseHandler.CU_PHOTO, checkUp.getImage().getFileName());
		if (checkUp.getFbPost() != null)
			values.put(DatabaseHandler.CU_FBPOSTID, checkUp.getFbPost().getId());

		db.update(DatabaseHandler.TABLE_CHECKUP, values, DatabaseHandler.CU_ID + "=" + checkUp.getEntryID(), null);
		db.close();
	}

	@Override
	public ArrayList<CheckUp> getAll() throws ParseException {
		ArrayList<CheckUp> cuList = new ArrayList<CheckUp>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseHandler.TABLE_CHECKUP;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Timestamp timestamp = DateTimeParser.getTimestamp(cursor
						.getString(1));
				PHRImage image = new PHRImage();
				image.setFileName(cursor.getString(6));
				Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
				String encoded = ImageHandler.encodeImageToBase64(bitmap);
				image.setEncodedImage(encoded);
				
				CheckUp cu = new CheckUp(cursor.getInt(0),
						new FBPost(cursor.getInt(7)),
						timestamp, 
						cursor.getString(5), 
						image,
						cursor.getString(2), 
						cursor.getString(3), 
						cursor.getString(4));

				cuList.add(cu);
			} while (cursor.moveToNext());
		}

		db.close();
		return cuList;
	}
}