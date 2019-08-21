package beans;

import java.util.List;

import assistive.UUIDGenerator;

public class Category {

	private String id;
	private String name;
	private String description;
	private List<Ad> ads;
	
	public boolean isActive;
	
	public Category() {
		this.id = UUIDGenerator.getUUID(); 
	}	
	
	public Category(String name) {
		this.id = UUIDGenerator.getUUID(); 
		this.name = name;
	}	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public List<Ad> getAds() {
		return ads;
	}
	public void setAds(List<Ad> ads) {
		this.ads = ads;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}	
	
}
