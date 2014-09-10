package com.example.phr.model;

import java.util.ArrayList;

public class GroupedStatus {

	private String month;
	private String day;
	private String average;
	private ArrayList<Status> weightStatusList;
	
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
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	public ArrayList<Status> getWeightStatusList() {
		return weightStatusList;
	}
	public void setWeightStatusList(ArrayList<Status> weightStatusList) {
		this.weightStatusList = weightStatusList;
	}

	
}
