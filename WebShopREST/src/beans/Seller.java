package beans;

import java.util.List;

public class Seller{
		
	private List<Ad> publishedAds;
	private List<Ad> deliveredProductAds;
	private List<Message> messages;
	private int numberOfLikes;
	private int numberOfDislikes;
	
	public Seller() {
	}
	
	public List<Ad> getPublishedAds() {
		return publishedAds;
	}
	public void setPublishedAds(List<Ad> publishedAds) {
		this.publishedAds = publishedAds;
	}
	public List<Ad> getDeliveredProductAds() {
		return deliveredProductAds;
	}
	public void setDeliveredProductAds(List<Ad> deliveredProductAds) {
		this.deliveredProductAds = deliveredProductAds;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
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
}
