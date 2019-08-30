package beans;

import java.util.ArrayList;

public class Seller{
		
	private ArrayList<Ad> publishedAds;
	private ArrayList<Ad> deliveredProductAds;
	private ArrayList<Message> messages;
	private int numberOfLikes;
	private int numberOfDislikes;
	private String name;
	
	public Seller() {
		this.publishedAds = new ArrayList<Ad>();
		this.deliveredProductAds = new ArrayList<Ad>();
		this.messages = new ArrayList<>();
		this.numberOfLikes = 0;
		this.numberOfDislikes = 0;
		this.name = "Seller";
	}
	
	public ArrayList<Ad> getPublishedAds() {
		return publishedAds;
	}
	public void setPublishedAds(ArrayList<Ad> publishedAds) {
		this.publishedAds = publishedAds;
	}
	public ArrayList<Ad> getDeliveredProductAds() {
		return deliveredProductAds;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDeliveredProductAds(ArrayList<Ad> deliveredProductAds) {
		this.deliveredProductAds = deliveredProductAds;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
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
	
	@Override
	public String toString() {
		return "\nRole: Seller";
	}
}
