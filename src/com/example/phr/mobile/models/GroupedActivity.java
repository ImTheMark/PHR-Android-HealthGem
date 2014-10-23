package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class GroupedActivity {
	Timestamp date;
	double calorieBurned;

	public GroupedActivity(Timestamp date, double calorieBurned) {
		super();
		this.date = date;
		this.calorieBurned = calorieBurned;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public double getCalorieBurned() {
		return calorieBurned;
	}

	public void setCalorieBurned(double calorieBurned) {
		this.calorieBurned = calorieBurned;
	}

}
