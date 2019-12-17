package beans;

import java.util.ArrayList;

import helpers.UUIDGenerator;

public class Category {
	
	private String id;

	private String name;
	private String description;
	private ArrayList<String> ads;
	
	public boolean isActive;
	
	public Category() {
		this.setId(UUIDGenerator.getUUID().toString());
		this.isActive = true;
		this.ads = new ArrayList<String>();
	}	
	
	public Category(String name) {
		this.name = name;
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getAds() {
		return ads;
	}
	public void setAds(ArrayList<String> ads) {
		this.ads = ads;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	
	@Override
	public String toString() {
		return this.id;
	}
	
}
