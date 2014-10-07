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
import com.example.phr.mobile.dao.MobileNoteDao;
import com.example.phr.mobile.models.FBPost;
import com.example.phr.mobile.models.Note;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class MobileNoteDaoImpl implements MobileNoteDao {

	@Override
	public void add(Note note) throws DataAccessException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.NOTES_ID, note.getEntryID());
		values.put(DatabaseHandler.NOTES_DATEADDED,
				fmt.format(note.getTimestamp()));
		values.put(DatabaseHandler.NOTES_NOTE, note.getNote());
		values.put(DatabaseHandler.NOTES_STATUS, note.getStatus());

		try {
			if (note.getImage() != null) {
				String encoded = note.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				note.getImage().setFileName(fileName);
				values.put(DatabaseHandler.NOTES_PHOTO, note.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.NOTES_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (note.getFbPost() != null)
			values.put(DatabaseHandler.NOTES_FBPOSTID, note.getFbPost().getId());

		db.insert(DatabaseHandler.TABLE_NOTES, null, values);
		db.close();
	}

	@Override
	public void edit(Note note) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.NOTES_DATEADDED,
				fmt.format(note.getTimestamp()));
		values.put(DatabaseHandler.NOTES_NOTE, note.getNote());
		values.put(DatabaseHandler.NOTES_STATUS, note.getStatus());

		try {
			if (note.getImage().getFileName() == null) {
				String encoded = note.getImage().getEncodedImage();
				String fileName = ImageHandler.saveImageReturnFileName(encoded);
				note.getImage().setFileName(fileName);
				values.put(DatabaseHandler.NOTES_PHOTO, note.getImage()
						.getFileName());
			} else
				values.putNull(DatabaseHandler.NOTES_PHOTO);
		} catch (FileNotFoundException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		} catch (ImageHandlerException e) {
			throw new DataAccessException("An error occurred in the DAO layer",
					e);
		}
		if (note.getFbPost() != null)
			values.put(DatabaseHandler.NOTES_FBPOSTID, note.getFbPost().getId());

		db.update(DatabaseHandler.TABLE_NOTES, values, DatabaseHandler.NOTES_ID
				+ "=" + note.getEntryID(), null);
		db.close();
	}

	@Override
	public ArrayList<Note> getAll() throws DataAccessException {
		ArrayList<Note> noteList = new ArrayList<Note>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_NOTES;

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Timestamp timestamp;
				try {
					timestamp = DateTimeParser
							.getTimestamp(cursor.getString(1));
				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}
				PHRImage image = new PHRImage();

				if (cursor.getString(4) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(4));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
					String encoded = ImageHandler.encodeImageToBase64(bitmap);
					image.setEncodedImage(encoded);
				}
				Note note = new Note(cursor.getInt(0), new FBPost(
						cursor.getInt(5)), timestamp, cursor.getString(3),
						image, cursor.getString(2));

				noteList.add(note);
			} while (cursor.moveToNext());
		}

		db.close();
		return noteList;
	}

	@Override
	public void delete(Note note) throws DataAccessException,
			EntryNotFoundException {
		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_NOTES, DatabaseHandler.NOTES_ID + "="
				+ note.getEntryID(), null);
		db.close();

	}

	@Override
	public List<Note> getAllReversed() throws DataAccessException {
		List<Note> noteList = new ArrayList<Note>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_NOTES
				+ " ORDER BY " + DatabaseHandler.NOTES_DATEADDED + " DESC";

		SQLiteDatabase db = DatabaseHandler.getDBHandler()
				.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Timestamp timestamp;
				try {
					timestamp = DateTimeParser
							.getTimestamp(cursor.getString(1));
				} catch (ParseException e) {
					throw new DataAccessException(
							"Cannot complete operation due to parse failure", e);
				}
				PHRImage image = new PHRImage();

				if (cursor.getString(4) == null)
					image = null;
				else {
					image.setFileName(cursor.getString(4));
					Bitmap bitmap = ImageHandler.loadImage(image.getFileName());
					String encoded = ImageHandler.encodeImageToBase64(bitmap);
					image.setEncodedImage(encoded);
				}

				Note note = new Note(cursor.getInt(0), new FBPost(
						cursor.getInt(5)), timestamp, cursor.getString(3),
						image, cursor.getString(2));

				noteList.add(note);
			} while (cursor.moveToNext());
		}

		db.close();
		return noteList;
	}
}