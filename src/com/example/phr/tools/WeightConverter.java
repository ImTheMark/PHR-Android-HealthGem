package com.example.phr.tools;

public class WeightConverter {

	
	public static double convertKgToLbs(double kg){
		double lb = 0;
		lb = kg * 2.2;
		return lb;
	}
	
	public static double convertLbsToKg(double lb){
		double kg = 0;
		kg = lb * 0.454;
		return kg;
	}
}
