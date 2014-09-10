package com.example.phr.model;

public class Retrieve_Food {

	String date;
	String time;
	String status;
	String guess;
	String what;
	String quantity;
	
	public Retrieve_Food(String date, String time, String status, String guess,
			String what, String quantity) {
		super();
		this.date = date;
		this.time = time;
		this.status = status;
		this.guess = guess;
		this.what = what;
		this.quantity = quantity;
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
	public String getGuess() {
		return guess;
	}
	public void setGuess(String guess) {
		this.guess = guess;
	}
	public String getWhat() {
		return what;
	}
	public void setWhat(String what) {
		this.what = what;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
	
}
