package com.example.phr.model;

public class GroupedFood {

	String month;
	String day;
	double calorie;
	double protein;
	double fat;
	double carbohydrates;
	
	
	public GroupedFood(String month, String day, double calorie,
			double protein, double fat, double carbohydrates) {
		super();
		this.month = month;
		this.day = day;
		this.calorie = calorie;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrates = carbohydrates;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
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
