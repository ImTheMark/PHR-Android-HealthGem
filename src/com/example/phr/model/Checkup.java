package com.example.phr.model;

public class Checkup {

	private String ailment;
	private String doctor;
	private String clinic;
	private String day;
	private String month;
	private String note;
	
	
	public Checkup(String ailment, String doctor, String clinic, String day,
			String month,String note) {
		super();
		this.ailment = ailment;
		this.doctor = doctor;
		this.clinic = clinic;
		this.day = day;
		this.month = month;
		this.note = note;
	}
	public String getAilment() {
		return ailment;
	}
	public void setAilment(String ailment) {
		this.ailment = ailment;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getClinic() {
		return clinic;
	}
	public void setClinic(String clinic) {
		this.clinic = clinic;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
