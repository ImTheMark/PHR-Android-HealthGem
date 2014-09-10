package com.example.phr.model;

public class Calorie {
	String month;
	String day;
	double foodCal;
	double activityCal;
	double totalIntakeCal;
	double recommendCal;
	String progress;
	int progressBar;
	
	
	public Calorie(String month, String day, double foodCal,
			double activityCal, double totalIntakeCal, double recommendCal,
			String progress, int progressBar) {
		super();
		this.month = month;
		this.day = day;
		this.foodCal = foodCal;
		this.activityCal = activityCal;
		this.totalIntakeCal = totalIntakeCal;
		this.recommendCal = recommendCal;
		this.progress = progress;
		this.progressBar = progressBar;
	}
	public int getProgressBar() {
		return progressBar;
	}
	public void setProgressBar(int progressBar) {
		this.progressBar = progressBar;
	}
	
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
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
	public double getFoodCal() {
		return foodCal;
	}
	public void setFoodCal(double foodCal) {
		this.foodCal = foodCal;
	}
	public double getActivityCal() {
		return activityCal;
	}
	public void setActivityCal(double activityCal) {
		this.activityCal = activityCal;
	}
	public double getTotalIntakeCal() {
		return totalIntakeCal;
	}
	public void setTotalIntakeCal(double totalIntakeCal) {
		this.totalIntakeCal = totalIntakeCal;
	}
	public double getRecommendCal() {
		return recommendCal;
	}
	public void setRecommendCal(double recommendCal) {
		this.recommendCal = recommendCal;
	}
	
}
