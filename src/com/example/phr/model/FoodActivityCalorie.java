package com.example.phr.model;

import android.graphics.drawable.Drawable;

public class FoodActivityCalorie {

	Drawable image;
	String calorie;
	String unit;
	double servingCount;
	String time;
	String action;

	public FoodActivityCalorie(Drawable image, String calorie, String unit,
			double servingCount, String time, String action) {
		super();
		this.image = image;
		this.calorie = calorie;
		this.unit = unit;
		this.servingCount = servingCount;
		this.time = time;
		this.action = action;
	}

	public double getServingCount() {
		return servingCount;
	}

	public void setServingCount(double servingCount) {
		this.servingCount = servingCount;
	}

	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}

	public String getCalorie() {
		return calorie;
	}

	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
