package com.example.phr.tools;

import android.annotation.SuppressLint;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTimeParser {

	@SuppressLint("SimpleDateFormat")
	public static String getDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
		return formatter.format(date);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getTime(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
		return formatter.format(date);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getMonth(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("MMM", Locale.ENGLISH);
		return formatter.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getMonthDay(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
		return formatter.format(date);
	}
	
	public static Date getSQLDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

}
