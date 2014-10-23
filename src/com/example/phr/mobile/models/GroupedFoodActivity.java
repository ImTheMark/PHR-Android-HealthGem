package com.example.phr.mobile.models;

import java.sql.Timestamp;
import java.util.List;

public class GroupedFoodActivity {
	Timestamp date;
	List<ActivityTrackerEntry> activityList;
	List<FoodTrackerEntry> foodList;
	double totalCalBurned;
	double totalCalIntake;
	double requireCal;

	public GroupedFoodActivity(Timestamp date,
			List<ActivityTrackerEntry> activityList,
			List<FoodTrackerEntry> foodList, double totalCalBurned,
			double totalCalIntake, double requireCal) {
		super();
		this.date = date;
		this.activityList = activityList;
		this.foodList = foodList;
		this.totalCalBurned = totalCalBurned;
		this.totalCalIntake = totalCalIntake;
		this.requireCal = requireCal;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public List<ActivityTrackerEntry> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<ActivityTrackerEntry> activityList) {
		this.activityList = activityList;
	}

	public List<FoodTrackerEntry> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<FoodTrackerEntry> foodList) {
		this.foodList = foodList;
	}

	public double getTotalCalBurned() {
		return totalCalBurned;
	}

	public void setTotalCalBurned(double totalCalBurned) {
		this.totalCalBurned = totalCalBurned;
	}

	public double getTotalCalIntake() {
		return totalCalIntake;
	}

	public void setTotalCalIntake(double totalCalIntake) {
		this.totalCalIntake = totalCalIntake;
	}

	public double getRequireCal() {
		return requireCal;
	}

	public void setRequireCal(double requireCal) {
		this.requireCal = requireCal;
	}

}
