package com.example.phr.model;


public class BloodPressure {
	// in millimeters of mercury
	int id;
	int systolic;
	int diastolic;
	String date;
	String time;
	String status;
	
	public BloodPressure(){
		
	}

	public BloodPressure(int systolic, int diastolic, String date, String time,
			String status) {
		super();
		this.systolic = systolic;
		this.diastolic = diastolic;
		this.date = date;
		this.time = time;
		this.status = status;
	}

	public int getSystolic() {
		return systolic;
	}

	public void setSystolic(int systolic) {
		this.systolic = systolic;
	}

	public int getDiastolic() {
		return diastolic;
	}

	public void setDiastolic(int diastolic) {
		this.diastolic = diastolic;
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
