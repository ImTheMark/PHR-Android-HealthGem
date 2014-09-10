package com.example.phr.model;

import java.math.BigDecimal;

import android.graphics.drawable.Drawable;

public class BloodSugar {
	
	long id;
	double glucoseLevel;
	String type;
	String description;
	String date;
	String time;
	Drawable image;
	

	public BloodSugar(long id, double mmol, String type, String description,
			String date, String time, Drawable image) {
		super();
		this.id = id;
		this.glucoseLevel = mmol;
		this.type = type;
		this.description = description;
		this.date = date;
		this.time = time;
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getGlucoseLevel() {
		return glucoseLevel;
	}

	public void setGlucoseLevel(double glucoseLevel) {
		this.glucoseLevel = glucoseLevel;
	}
	
	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
