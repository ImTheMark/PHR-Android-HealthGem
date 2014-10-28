package phr.models;

public class Restaurant {
	
	Integer entryID;
	String name;
	
	public Restaurant(Integer entryID, String name) {
		super();
		this.entryID = entryID;
		this.name = name;
	}

	public Restaurant(int entryID) {
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
