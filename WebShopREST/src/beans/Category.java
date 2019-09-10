package beans;

import java.util.ArrayList;

public class Category {

	private String name;
	private String description;
	private ArrayList<String> ads;
	
	public boolean isActive;
	
	public Category() {
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}	
	
}
