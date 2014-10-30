package com.example.phr.tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

public class DateTimeParser {

	@SuppressLint("SimpleDateFormat")
	public static String getDate(Timestamp date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy",
				Locale.ENGLISH);
		return formatter.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getDateTime(Timestamp date) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
		return formatter.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getTime(Timestamp date) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm",
				Locale.ENGLISH);
		return formatter.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getMonth(Timestamp date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM", Locale.ENGLISH);
		return formatter.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getDay(Timestamp date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd", Locale.ENGLISH);
		return formatter.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getYear(Timestamp date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy",
				Locale.ENGLISH);
		return formatter.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getMonthDay(Timestamp date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd",
				Locale.ENGLISH);
		return formatter.format(date);
	}

	public static Timestamp getTimestamp(String text) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		Date date = dateFormat.parse(text);
		Timestamp timestamp = new Timestamp(date.getTime());

		return timestamp;
	}

}
