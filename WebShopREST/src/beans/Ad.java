package beans;

import java.util.List;

public class Ad{
	
	private String name;
	private double price;
	private String description;
	private int numberOfLikes;
	private int numberOfDislikes;
	private String image;
	private String postingDate;
	private String expirationDate;
	private List<Review> reviews;
	private String city;
	
	private boolean isActive;
	
	public Ad() {
	}
	
	public Ad(String name, double price, String image) {
		this.name = name;
		this.price = price;
		this.image = image;
	}
	
	public Ad(String name, double price, String description, int numberOfLikes, int numberOfDislikes, String image,
			String postingDate, String expirationDate, List<Review> reviews, String city, boolean isActive) {
		this.name = name;
		this.price = price;
		this.description = description;
		this.numberOfLikes = numberOfLikes;
		this.numberOfDislikes = numberOfDislikes;
		this.image = image;
		this.postingDate = postingDate;
		this.expirationDate = expirationDate;
		this.reviews = reviews;
		this.city = city;
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNumberOfLikes() {
		return numberOfLikes;
	}
	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}
	public int getNumberOfDislikes() {
		return numberOfDislikes;
	}
	public void setNumberOfDislikes(int numberOfDislikes) {
		this.numberOfDislikes = numberOfDislikes;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}	
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;		
	}
	
}
