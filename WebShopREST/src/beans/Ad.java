package beans;

import java.util.ArrayList;

public class Ad implements Comparable<Ad>{
	
	private String name;
	private double price;

	private String description;
	private String image;
	private String city;
	private String expirationDate;
	
	private String postingDate;
	
	private int numberOfLikes;
	private int numberOfDislikes;
	private ArrayList<Review> reviews;

	private Status status;

	public static enum Status {
	    DELIVERED,
	    PENDING,
	    PUBLISHED,
	    DELETED
	};
	
	public Ad() {
		this.postingDate = java.time.LocalDate.now().toString();
		this.numberOfDislikes = 0;
		this.numberOfDislikes = 0;
		this.reviews = new ArrayList<Review>(); 
		this.status = Status.PUBLISHED;
	}
	
	public Ad(String name, double price, String image) {
		this.name = name;
		this.price = price;
		this.image = image;
	}
	
	public Ad(String name, double price, String description, int numberOfLikes, int numberOfDislikes, String image,
			String postingDate, String expirationDate, ArrayList<Review> reviews, String city) {
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
	}
	
	public Ad(String name, double price, String description, int numberOfLikes, int numberOfDislikes, String image,
			String postingDate, String expirationDate, ArrayList<Review> reviews, String city, Status status) {
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
		this.status = status;
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
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}	
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}	
	
	@Override
	public String toString() {
		return "Ad [name=" + name + ", price=" + price + ", description=" + description + ", image=" + image + ", city="
				+ city + ", expirationDate=" + expirationDate + ", postingDate=" + postingDate + ", numberOfLikes="
				+ numberOfLikes + ", numberOfDislikes=" + numberOfDislikes + ", reviews=" + reviews + ", status="
				+ status + "]";
	}

	@Override
	public int compareTo(Ad ad) {
		int thisDifference = this.numberOfLikes - this.numberOfDislikes;
		int adDifference = ad.getNumberOfLikes() - ad.getNumberOfDislikes();
		
		return (thisDifference > adDifference) ? -1 :
				(thisDifference == adDifference ? 0 : 1);
	}
	
}
