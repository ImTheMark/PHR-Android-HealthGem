package com.example.phr.tools;

import android.annotation.SuppressLint;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateTimeParser {

	@SuppressLint("SimpleDateFormat")
	public static String getDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		return formatter.format(date);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getTime(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		return formatter.format(date);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getMonth(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("MMM");
		return formatter.format(date);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static java.sql.Date getSQLDate(java.util.Date date){
		return new java.sql.Date(date.getTime());
	}

}
