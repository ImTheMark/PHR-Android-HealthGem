package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class UnverifiedFoodEntry extends TrackerEntry {

	String foodName;
	Double calorie;
	Double protein;
	Double fat;
	Double carbohydrate;
	String servingUnit;
	Double servingSize;

	public UnverifiedFoodEntry(Integer entryID, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			String foodName, Double calorie, Double protein, Double fat,
			Double carbohydrate, String servingUnit, Double servingSize) {
		super(entryID, facebookID, timestamp, status, image);
		this.foodName = foodName;
		this.calorie = calorie;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrate = carbohydrate;
		this.servingUnit = servingUnit;
		this.servingSize = servingSize;
	}

	public UnverifiedFoodEntry(User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			String foodName, Double calorie, Double protein, Double fat,
			Double carbohydrate, String servingUnit, Double servingSize) {
		super(user, facebookID, timestamp, status, image);
		this.foodName = foodName;
		this.calorie = calorie;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrate = carbohydrate;
		this.servingUnit = servingUnit;
		this.servingSize = servingSize;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public Double getCalorie() {
		return calorie;
	}

	public void setCalorie(Double calorie) {
		this.calorie = calorie;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(Double protein) {
		this.protein = protein;
	}

	public Double getFat() {
		return fat;
	}

	public void setFat(Double fat) {
		this.fat = fat;
	}

	public Double getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(Double carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public String getServingUnit() {
		return servingUnit;
	}

	public void setServingUnit(String servingUnit) {
		this.servingUnit = servingUnit;
	}

	public Double getServingSize() {
		return servingSize;
	}

	public void setServingSize(Double servingSize) {
		this.servingSize = servingSize;
	}
}
