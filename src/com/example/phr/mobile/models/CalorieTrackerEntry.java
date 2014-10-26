package com.example.phr.mobile.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class CalorieTrackerEntry implements Serializable {

	Timestamp date;
	List<TrackerEntry> list;
	double totalCalBurned;
	double totalCalIntake;
	double requireCal;

	public CalorieTrackerEntry() {
		super();
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public List<TrackerEntry> getList() {
		return list;
	}

	public void setList(List<TrackerEntry> list) {
		this.list = list;
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
