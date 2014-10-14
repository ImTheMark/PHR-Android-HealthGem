package com.example.phr.mobile.models;

import java.io.Serializable;

public class Food implements Serializable {

	Integer entryID;
	String name;
	double calorie;
	double protein;
	double fat;
	double carbohydrate;
	String servingUnit;
	double servingSize;
	Integer restaurantID;
	Boolean fromFatsecret;

	public Food(Integer entryID) {
		super();
		this.entryID = entryID;
	}

	public Food(Integer entryID, String name, double calorie, double protein,
			double fat, double carbohydrate, String servingUnit,
			double servingSize, Integer restaurantID, Boolean fromFatsecret) {
		super();
		this.entryID = entryID;
		this.name = name;
		this.calorie = calorie;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrate = carbohydrate;
		this.servingUnit = servingUnit;
		this.servingSize = servingSize;
		this.restaurantID = restaurantID;
		this.fromFatsecret = fromFatsecret;
	}

	public Food(String name, double calorie, double protein, double fat,
			double carbohydrate, String servingUnit, double servingSize,
			Integer restaurantID, Boolean fromFatsecret) {
		super();
		this.name = name;
		this.calorie = calorie;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrate = carbohydrate;
		this.servingUnit = servingUnit;
		this.servingSize = servingSize;
		this.restaurantID = restaurantID;
		this.fromFatsecret = fromFatsecret;
	}

	public Integer getEntryID() {
		return entryID;
	}

	public void setEntryID(Integer entryID) {
		this.entryID = entryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(double carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public String getServingUnit() {
		return servingUnit;
	}

	public void setServingUnit(String servingUnit) {
		this.servingUnit = servingUnit;
	}

	public double getServingSize() {
		return servingSize;
	}

	public void setServingSize(double servingSize) {
		this.servingSize = servingSize;
	}

	public Integer getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(Integer restaurantID) {
		this.restaurantID = restaurantID;
	}

	public Boolean getFromFatsecret() {
		return fromFatsecret;
	}

	public void setFromFatsecret(Boolean fromFatsecret) {
		this.fromFatsecret = fromFatsecret;
	}
}
