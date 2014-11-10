package com.example.phr.mobile.models;

import java.io.Serializable;

public class Activity implements Serializable {

	Integer entryID;
	String name;
	double MET;

	public Activity(Integer entryID) {
		super();
		this.entryID = entryID;
	}

	public Activity(Integer entryID, String name, double mET) {
		super();
		this.entryID = entryID;
		this.name = name;
		MET = mET;
	}

	public Activity(String name, double mET) {
		super();
		this.name = name;
		MET = mET;
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

	public double getMET() {
		return MET;
	}

	public void setMET(double mET) {
		MET = mET;
	}

}
