package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class GroupedFood implements Serializable {

	Timestamp date;
	double calorie;
	double protein;
	double fat;
	double carbohydrates;

	public GroupedFood(Timestamp date, double calorie, double protein,
			double fat, double carbohydrates) {
		super();
		this.date = date;
		this.calorie = calorie;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrates = carbohydrates;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public double getCalorie() {
		return calorie;
	}

	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

}
