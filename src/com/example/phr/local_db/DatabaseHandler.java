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
	
	public static final String KEY_USERNAME = "username";
	public static final String KEY_ACCESSTOKEN = "token";
	
	// ACTIVITY DATABASE
	public static final String TABLE_ACTIVITY = "activitytracker";
	public static final String ACT_ID = "id";
	public static final String ACT_DATEADDED = "dateAdded";
	public static final String ACT_ACTIVITYID = "activityID";
	public static final String ACT_CALORIEBURNED = "calorieBurnedPerHour";
	public static final String ACT_STATUS = "status";
	public static final String ACT_PHOTO = "photo";
	public static final String ACT_FBPOSTID = "fbPostID";
	
	// BLOOD PRESSURE DATABASE
	public static final String TABLE_BLOODPRESSURE = "bloodpressuretracker";
	public static final String BP_ID = "id";
	public static final String BP_DATEADDED = "dateAdded";
	public static final String BP_SYSTOLIC = "systolic";
	public static final String BP_DIASTOLIC = "diastolic";
	public static final String BP_STATUS = "status";
	public static final String BP_PHOTO = "photo";
	public static final String BP_FBPOSTID = "fbPostID";
	
	// BLOOD SUGAR DATABASE
	public static final String TABLE_BLOODSUGAR = "bloodsugartracker";
	public static final String BS_ID = "id";
	public static final String BS_DATEADDED = "dateAdded";
	public static final String BS_BLOODSUGAR = "bloodSugar";
	public static final String BS_TYPE = "type";
	public static final String BS_STATUS = "status";
	public static final String BS_PHOTO = "photo";
	public static final String BS_FBPOSTID = "fbPostID";
	
	// CHECKUP DATABASE
	public static final String TABLE_CHECKUP = "checkuptracker";
	public static final String CU_ID = "id";
	public static final String CU_DATEADDED = "dateAdded";
	public static final String CU_PURPOSE = "purpose";
	public static final String CU_DOCTORNAME = "doctorsName";
	public static final String CU_NOTES = "notes";
	public static final String CU_STATUS = "status";
	public static final String CU_PHOTO = "photo";
	public static final String CU_FBPOSTID = "fbPostID";
	
	// FOOD DATABASE
	public static final String TABLE_FOOD = "foodtracker";
	public static final String FOOD_ID = "id";
	public static final String FOOD_DATEADDED = "dateAdded";
	public static final String FOOD_FOODID = "foodID";
	public static final String FOOD_SERVINGCOUNT = "servingCount";
	public static final String FOOD_STATUS = "status";
	public static final String FOOD_PHOTO = "photo";
	public static final String FOOD_FBPOSTID = "fbPostID";
	
	// NOTES DATABASE
	public static final String TABLE_NOTES = "notestracker";
	public static final String NOTES_ID = "id";
	public static final String NOTES_DATEADDED = "dateAdded";
	public static final String NOTES_TITLE = "title";
	public static final String NOTES_NOTE = "note";
	public static final String NOTES_STATUS = "status";
	public static final String NOTES_PHOTO = "photo";
	public static final String NOTES_FBPOSTID = "fbPostID";
	
	// WEIGHT DATABASE
	public static final String TABLE_WEIGHT = "weighttracker";
	public static final String WEIGHT_ID = "id";
	public static final String WEIGHT_DATEADDED = "dateAdded";
	public static final String WEIGHT_POUNDS = "weightInPounds";
	public static final String WEIGHT_STATUS = "status";
	public static final String WEIGHT_PHOTO = "photo";
	public static final String WEIGHT_FBPOSTID = "fbPostID";
	

	private static final DatabaseHandler dbHandler = new DatabaseHandler(HealthGem.getContext());

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DatabaseHandler getDBHandler() {
		return dbHandler;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		
		String CREATE_ACCESSTOKEN_TABLE = "CREATE TABLE " + TABLE_ACCESSTOKEN
				+ "(" + KEY_ACCESSTOKEN + " TEXT, " + KEY_USERNAME + " TEXT"
				+ ")";
		
		
		String CREATE_ACTIVITY_TABLE = "CREATE TABLE "
				+ TABLE_ACTIVITY + "(" 
				+ ACT_ID + " INTEGER PRIMARY KEY ," 
				+ ACT_DATEADDED + " TEXT,"
				+ ACT_ACTIVITYID + " INTEGER," 
				+ ACT_CALORIEBURNED + " INTEGER," 
				+ ACT_STATUS + " TEXT,"
				+ ACT_PHOTO + " TEXT,"  
				+ ACT_FBPOSTID + " INTEGER"  
				+ ")";
		
		
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
		
		
		String CREATE_BLOODSUGAR_TABLE = "CREATE TABLE "
				+ TABLE_BLOODSUGAR + "(" 
				+ BS_ID + " INTEGER PRIMARY KEY ," 
				+ BS_DATEADDED + " TEXT,"
				+ BS_BLOODSUGAR + " REAL," 
				+ BS_TYPE + " TEXT," 
				+ BS_STATUS + " TEXT,"
				+ BS_PHOTO + " TEXT,"  
				+ BS_FBPOSTID + " INTEGER"  
				+ ")";
		
		
		String CREATE_CHECKUP_TABLE = "CREATE TABLE "
				+ TABLE_CHECKUP + "(" 
				+ CU_ID + " INTEGER PRIMARY KEY ," 
				+ CU_DATEADDED + " TEXT,"
				+ CU_PURPOSE + " TEXT," 
				+ CU_DOCTORNAME + " TEXT," 
				+ CU_NOTES + " TEXT," 
				+ CU_STATUS + " TEXT,"
				+ CU_PHOTO + " TEXT,"  
				+ CU_FBPOSTID + " INTEGER"  
				+ ")";
		
		
		String CREATE_FOOD_TABLE = "CREATE TABLE "
				+ TABLE_FOOD + "(" 
				+ FOOD_ID + " INTEGER PRIMARY KEY ," 
				+ FOOD_DATEADDED + " TEXT,"
				+ FOOD_FOODID + " INTEGER," 
				+ FOOD_SERVINGCOUNT + " INTEGER," 
				+ FOOD_STATUS + " TEXT,"
				+ FOOD_PHOTO + " TEXT,"  
				+ FOOD_FBPOSTID + " INTEGER"  
				+ ")";
		
		
		String CREATE_NOTES_TABLE = "CREATE TABLE "
				+ TABLE_NOTES + "(" 
				+ NOTES_ID + " INTEGER PRIMARY KEY ," 
				+ NOTES_DATEADDED + " TEXT,"
				+ NOTES_TITLE + " TEXT," 
				+ NOTES_NOTE + " TEXT," 
				+ NOTES_STATUS + " TEXT,"
				+ NOTES_PHOTO + " TEXT,"  
				+ NOTES_FBPOSTID + " INTEGER"  
				+ ")";
		
		
		String CREATE_WEIGHT_TABLE = "CREATE TABLE "
				+ TABLE_WEIGHT + "(" 
				+ WEIGHT_ID + " INTEGER PRIMARY KEY ," 
				+ WEIGHT_DATEADDED + " TEXT,"
				+ WEIGHT_POUNDS + " REAL," 
				+ WEIGHT_STATUS + " TEXT,"
				+ WEIGHT_PHOTO + " TEXT,"  
				+ WEIGHT_FBPOSTID + " INTEGER"  
				+ ")";
		
		
		db.execSQL(CREATE_ACCESSTOKEN_TABLE);
		db.execSQL(CREATE_ACTIVITY_TABLE);
		db.execSQL(CREATE_BLOODPRESSURE_TABLE);
		db.execSQL(CREATE_BLOODSUGAR_TABLE);
		db.execSQL(CREATE_CHECKUP_TABLE);
		db.execSQL(CREATE_FOOD_TABLE);
		db.execSQL(CREATE_NOTES_TABLE);
		db.execSQL(CREATE_WEIGHT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCESSTOKEN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOODPRESSURE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOODSUGAR);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKUP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);

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
}
