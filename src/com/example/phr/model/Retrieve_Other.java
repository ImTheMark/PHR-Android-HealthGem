package com.example.phr.model;

public class Retrieve_Other {

	String date;
	String time;
	String status;
	
	public Retrieve_Other(String date, String time, String status) {
		super();
		this.date = date;
		this.time = time;
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
