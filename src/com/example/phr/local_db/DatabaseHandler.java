package com.example.phr.local_db;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.phr.application.HealthGem;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.model.AccessToken;
import com.example.phr.model.Client;

public class DatabaseHandler extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "HealthGem";
	public static final String TABLE_ACCESSTOKEN = "accesstoken";
	public static final String TABLE_CLIENT = "client";
	
	public static final String KEY_USERNAME = "username";
	public static final String KEY_ACCESSTOKEN = "token";
	public static final String KEY_CLIENTID = "clientID";
	public static final String KEY_CLIENTPASSWORD = "clientPassword";

	// BP DATABASE
	public static final String TABLE_BLOODPRESSURE = "bloodpressuretracker";
	public static final String BP_ID = "id";
	public static final String BP_DATEADDED = "dateAdded";
	public static final String BP_SYSTOLIC = "systolic";
	public static final String BP_DIASTOLIC = "diastolic";
	public static final String BP_STATUS = "status";
	public static final String BP_PHOTO = "photo";
	public static final String BP_FBPOSTID = "fbPostID";

	private static final DatabaseHandler dbHandler = new DatabaseHandler(
			HealthGem.getContext());;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DatabaseHandler getDBHandler() {
		return dbHandler;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_BLOODPRESSURE_TABLE = "CREATE TABLE "
				+ TABLE_BLOODPRESSURE + "(" 
				+ BP_ID + " INTEGER PRIMARY KEY ," 
				+ BP_DATEADDED + " TEXT,"
				+ BP_SYSTOLIC + " INTEGER," 
				+ BP_DIASTOLIC + " INTEGER," 
				+ BP_STATUS + " TEXT,"
				+ BP_PHOTO + " TEXT,"  
				+ BP_FBPOSTID + " INTEGER"  
				+ ")";
		String CREATE_ACCESSTOKEN_TABLE = "CREATE TABLE " + TABLE_ACCESSTOKEN
				+ "(" + KEY_ACCESSTOKEN + " TEXT, " + KEY_USERNAME + " TEXT"
				+ ")";
		String CREATE_CLIENT_TABLE = "CREATE TABLE " + TABLE_CLIENT + "("
				+ KEY_CLIENTID + " TEXT, " + KEY_CLIENTPASSWORD + " TEXT" + ")";
		db.execSQL(CREATE_BLOODPRESSURE_TABLE);
		db.execSQL(CREATE_ACCESSTOKEN_TABLE);
		db.execSQL(CREATE_CLIENT_TABLE);

		//initClient(db);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOODPRESSURE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCESSTOKEN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);

		// Create tables again
		onCreate(db);
	}

	public AccessToken getAccessToken() {
		AccessToken token = new AccessToken();
		String selectQuery = "SELECT  * FROM " + TABLE_ACCESSTOKEN;

		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				token.setAccessToken(cursor.getString(0));
				token.setUserName(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		return token;
	}

/*	public Client getClient() {
		Client c = new Client();
		String selectQuery = "SELECT  * FROM " + TABLE_CLIENT;

		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				c.setClientID(EncryptionHandler.decrypt(cursor.getString(0)));
				c.setClientPassword(EncryptionHandler.decrypt(cursor
						.getString(1)));
			} while (cursor.moveToNext());
		}
		db.close();
		return c;
	}*/

	public void setAccessToken(AccessToken accessToken) {
		SQLiteDatabase db = dbHandler.getWritableDatabase();

		db.delete(TABLE_ACCESSTOKEN, null, null);
		// db.execSQL("delete * from " + TABLE_ACCESSTOKEN);

		ContentValues values = new ContentValues();
		values.put(KEY_ACCESSTOKEN, accessToken.getAccessToken());
		values.put(KEY_USERNAME, accessToken.getUserName());

		db.insert(TABLE_ACCESSTOKEN, null, values);
		db.close();
	}

/*	public void setClient(Client c) {
		SQLiteDatabase db = dbHandler.getWritableDatabase();

		db.delete(TABLE_CLIENT, null, null);
		// db.execSQL("delete * from " + TABLE_ACCESSTOKEN);

		ContentValues values = new ContentValues();
		values.put(KEY_CLIENTID, EncryptionHandler.encrypt(c.getClientID()));
		values.put(KEY_CLIENTPASSWORD,
				EncryptionHandler.encrypt(c.getClientPassword()));
		System.out.println("Have set client id and password");
		db.insert(TABLE_CLIENT, null, values);
		db.close();
	}*/

/*	public void initClient(SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		Client c = new Client();
		c.setClientID("9543ED1349084DA816F103234217FED7A8627621");
		c.setClientPassword("Y9xSazM4fHrkNd8tMKPkbjeqKAl4YE8QXGiJ");
		values.put(KEY_CLIENTID, EncryptionHandler.encrypt(c.getClientID()));
		values.put(KEY_CLIENTPASSWORD,
				EncryptionHandler.encrypt(c.getClientPassword()));
		System.out.println("Have set client id and password");
		db.insert(TABLE_CLIENT, null, values);
		// db.close();
	}*/
}
