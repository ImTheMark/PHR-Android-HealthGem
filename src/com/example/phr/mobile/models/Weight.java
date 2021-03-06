package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class Weight extends TrackerEntry {

	private double weightInPounds;

	public Weight(String facebookID, Timestamp timestamp, String status,
			PHRImage image) {
		super(facebookID, timestamp, status, image);
		// TODO Auto-generated constructor stub
	}

	public Weight(Integer entryID, String facebookID, Timestamp timestamp,
			String status, PHRImage image) {
		super(entryID, facebookID, timestamp, status, image);
		// TODO Auto-generated constructor stub
	}

	public Weight(Integer entryID, Timestamp timestamp, String status,
			PHRImage image) {
		super(entryID, timestamp, status, image);
		// TODO Auto-generated constructor stub
	}

	public Weight(Timestamp timestamp, String status, PHRImage image) {
		super(timestamp, status, image);
		// TODO Auto-generated constructor stub
	}

	public Weight(Integer entryID, String facebookID, Timestamp timestamp,
			String status, PHRImage image, double weightInPounds) {
		super(entryID, facebookID, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(String facebookID, Timestamp timestamp, String status,
			PHRImage image, double weightInPounds) {
		super(facebookID, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Integer entryID, Timestamp timestamp, String status,
			PHRImage image, double weightInPounds) {
		super(entryID, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Timestamp timestamp, String status, PHRImage image,
			double weightInPounds) {
		super(timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public double getWeightInPounds() {
		return weightInPounds;
	}

	public void setWeightInPounds(double weightInPounds) {
		this.weightInPounds = weightInPounds;
	}

	public double getWeightInKilograms() {
		return weightInPounds / 2.2;
	}

	public void setWeightInKilograms(double weightInKilograms) {
		weightInPounds = weightInKilograms * 2.2;
	}
}
