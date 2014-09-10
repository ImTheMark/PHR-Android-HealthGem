package com.example.phr.model;

public class FoodSingle {
	
	String food;
	String serving;
	double fat;
	double cal;
	double carbs;
	double protein;
	String time;
	public FoodSingle(String food, String serving, double fat, double cal,
			double carbs, double protein, String time) {
		super();
		this.food = food;
		this.serving = serving;
		this.fat = fat;
		this.cal = cal;
		this.carbs = carbs;
		this.protein = protein;
		this.time = time;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public String getServing() {
		return serving;
	}
	public void setServing(String serving) {
		this.serving = serving;
	}
	public double getFat() {
		return fat;
	}
	public void setFat(double fat) {
		this.fat = fat;
	}
	public double getCal() {
		return cal;
	}
	public void setCal(double cal) {
		this.cal = cal;
	}
	public double getCarbs() {
		return carbs;
	}
	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}
	public double getProtein() {
		return protein;
	}
	public void setProtein(double protein) {
		this.protein = protein;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
