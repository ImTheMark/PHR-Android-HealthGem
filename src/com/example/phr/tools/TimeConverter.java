package com.example.phr.tools;

public class TimeConverter {
	
	public static int convertMinToSec(int min){
		int sec = 0;
		sec = min*60;
		return sec;
	}
	
	public static int convertHrToSec (int hr){
		int sec = 0;
		sec = hr*3600;
		return sec;
	}

}
