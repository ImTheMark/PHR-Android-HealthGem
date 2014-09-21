package com.example.phr.mobile.models;

import java.sql.Timestamp;

public class Weight extends TrackerEntry {

	private double weightInPounds;

	public Weight(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, double weightInPounds) {
		super(entryID, fbPost, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, double weightInPounds) {
		super(fbPost, timestamp, status, image);
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
