package com.example.phr.tools;

import java.sql.Timestamp;

public class TimestampHandler {

	public static Timestamp getCurrentTimestamp() {
		java.util.Date date = new java.util.Date();
		return new Timestamp(date.getTime());
	}
}
