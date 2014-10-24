package phr.models;

import java.sql.Timestamp;

public class UnverifiedFoodEntry {

	Integer entryID;
	Timestamp timestamp;
	String foodName;
	Double calorie;
	Double protein;
	Double fat;
	Double carbohydrate;
	String servingUnit;
	Double servingSize;
	String status;
	PHRImage image;
	String fbPostID;

	public UnverifiedFoodEntry(Integer entryID, Timestamp timestamp,
			String foodName, Double calorie, Double protein, Double fat,
			Double carbohydrate, String servingUnit, Double servingSize,
			String status, PHRImage image, String fbPostID) {
		super();
		this.entryID = entryID;
		this.timestamp = timestamp;
		this.foodName = foodName;
		this.calorie = calorie;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrate = carbohydrate;
		this.servingUnit = servingUnit;
		this.servingSize = servingSize;
		this.status = status;
		this.image = image;
		this.fbPostID = fbPostID;
	}

	public UnverifiedFoodEntry(Timestamp timestamp, String foodName,
			Double calorie, Double protein, Double fat, Double carbohydrate,
			String servingUnit, Double servingSize, String status,
			PHRImage image, String fbPostID) {
		super();
		this.timestamp = timestamp;
		this.foodName = foodName;
		this.calorie = calorie;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrate = carbohydrate;
		this.servingUnit = servingUnit;
		this.servingSize = servingSize;
		this.status = status;
		this.image = image;
		this.fbPostID = fbPostID;
	}

	public Integer getEntryID() {
		return entryID;
	}

	public void setEntryID(Integer entryID) {
		this.entryID = entryID;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public Double getCalorie() {
		return calorie;
	}

	public void setCalorie(Double calorie) {
		this.calorie = calorie;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(Double protein) {
		this.protein = protein;
	}

	public Double getFat() {
		return fat;
	}

	public void setFat(Double fat) {
		this.fat = fat;
	}

	public Double getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(Double carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public String getServingUnit() {
		return servingUnit;
	}

	public void setServingUnit(String servingUnit) {
		this.servingUnit = servingUnit;
	}

	public Double getServingSize() {
		return servingSize;
	}

	public void setServingSize(Double servingSize) {
		this.servingSize = servingSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PHRImage getImage() {
		return image;
	}

	public void setImage(PHRImage image) {
		this.image = image;
	}

	public String getFbPostID() {
		return fbPostID;
	}

	public void setFbPostID(String fbPostID) {
		this.fbPostID = fbPostID;
	}

}
