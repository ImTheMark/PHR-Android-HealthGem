package com.example.phr.model;

public class Note {

	String title;
	String note;
	String date;
	String time;
	
	public Note(String title, String note, String date, String time) {
		super();
		this.title = title;
		this.note = note;
		this.date = date;
		this.time = time;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	
	
}
