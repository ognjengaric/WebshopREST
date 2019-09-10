package beans;

import java.util.ArrayList;

public class Buyer{
	
	private ArrayList<String> orderedProductAds;
	private ArrayList<String> deliveredProductAds;
	private ArrayList<String> favoriteAds;
	private String name;
	
	
	public Buyer() {
		this.orderedProductAds = new ArrayList<String>();
		this.deliveredProductAds = new ArrayList<String>();
		this.favoriteAds = new ArrayList<String>();
		this.name = "Buyer";
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getOrderedProductAds() {
		return orderedProductAds;
	}
	public void setOrderedProductAds(ArrayList<String> orderedProductAds) {
		this.orderedProductAds = orderedProductAds;
	}
	public ArrayList<String> getDeliveredProductAds() {
		return deliveredProductAds;
	}
	public void setDeliveredProductAds(ArrayList<String> deliveredProductAds) {
		this.deliveredProductAds = deliveredProductAds;
	}
	public ArrayList<String> getFavoriteAds() {
		return favoriteAds;
	}
	public void setFavoriteAds(ArrayList<String> favoriteAds) {
		this.favoriteAds = favoriteAds;
	}


	@Override
	public String toString() {
		return "Buyer";
	}
	
	
}
