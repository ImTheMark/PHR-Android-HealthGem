package com.example.phr.mobile.models;

import java.io.Serializable;

public class SportEstablishment implements Serializable {

	Integer entryID;
	String name;

	public SportEstablishment(Integer entryID, String name) {
		super();
		this.entryID = entryID;
		this.name = name;
	}

	public SportEstablishment(int entryID) {
		super();
		this.entryID = entryID;
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
}
