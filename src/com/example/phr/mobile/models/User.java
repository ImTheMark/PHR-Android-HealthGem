package com.example.phr.mobile.models;


public class User {

	private int id;
	private String username;
	private String password;
	private String name;
	private String dateOfBirth;
	private String gender;
	private double height;
	private double weight;
	private String contactNumber;
	private String email;
	private String emergencyPerson;
	private String emergencyContactNumber;
	private String allergies;
	private String knownHealthProblems;
	private String fbAccessToken;

	public User() {
		super();
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String name, String dateOfBirth) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public User(int id, String username, String password, String name,
			String dateOfBirth) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public User(int id, String username, String password, String name,
			String dateOfBirth, String gender, double height, double weight,
			String contactNumber, String email, String emergencyPerson,
			String emergencyContactNumber, String allergies,
			String knownHealthProblems) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.contactNumber = contactNumber;
		this.email = email;
		this.emergencyPerson = emergencyPerson;
		this.emergencyContactNumber = emergencyContactNumber;
		this.allergies = allergies;
		this.knownHealthProblems = knownHealthProblems;
	}
	
	

	public String getFbAccessToken() {
		return fbAccessToken;
	}

	public void setFbAccessToken(String fbAccessToken) {
		this.fbAccessToken = fbAccessToken;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmergencyPerson() {
		return emergencyPerson;
	}

	public void setEmergencyPerson(String emergencyPerson) {
		this.emergencyPerson = emergencyPerson;
	}

	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}

	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getKnownHealthProblems() {
		return knownHealthProblems;
	}

	public void setKnownHealthProblems(String knownHealthProblems) {
		this.knownHealthProblems = knownHealthProblems;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
