package beans;

import java.util.ArrayList;

public class Seller{
		
	private ArrayList<String> publishedAds;
	private ArrayList<String> deliveredProductAds;
	private ArrayList<String> pendingProductAds;
	private ArrayList<Message> messages;
	private int numberOfLikes;
	private int numberOfDislikes;
	private String name;
	
	public Seller() {
		this.publishedAds = new ArrayList<String>();
		this.deliveredProductAds = new ArrayList<String>();
		this.pendingProductAds = new ArrayList<String>();
		this.messages = new ArrayList<>();
		this.numberOfLikes = 0;
		this.numberOfDislikes = 0;
		this.name = "Seller";
	}
	
	public ArrayList<String> getPublishedAds() {
		return publishedAds;
	}
	public void setPublishedAds(ArrayList<String> publishedAds) {
		this.publishedAds = publishedAds;
	}
	public ArrayList<String> getDeliveredProductAds() {
		return deliveredProductAds;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDeliveredProductAds(ArrayList<String> deliveredProductAds) {
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
	
	public ArrayList<String> getPendingProductAds() {
		return pendingProductAds;
	}

	public void setPendingProductAds(ArrayList<String> pendingProductAds) {
		this.pendingProductAds = pendingProductAds;
	}
	
	
	@Override
	public String toString() {
		return "\nRole: Seller \nPublished: "+ this.publishedAds +"\nPending:" + this.pendingProductAds + "\nDelivered:" + this.deliveredProductAds;
	}
}
