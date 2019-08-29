package beans;

import java.util.ArrayList;

public class Buyer{
	
	private ArrayList<Ad> orderedProductAds;
	private ArrayList<Ad> deliveredProductAds;
	private ArrayList<Ad> favoriteAds;
	private String name;
	
	
	public Buyer() {
		this.orderedProductAds = new ArrayList<Ad>();
		this.deliveredProductAds = new ArrayList<Ad>();
		this.favoriteAds = new ArrayList<Ad>();
		this.name = "Buyer";
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Ad> getOrderedProductAds() {
		return orderedProductAds;
	}
	public void setOrderedProductAds(ArrayList<Ad> orderedProductAds) {
		this.orderedProductAds = orderedProductAds;
	}
	public ArrayList<Ad> getDeliveredProductAds() {
		return deliveredProductAds;
	}
	public void setDeliveredProductAds(ArrayList<Ad> deliveredProductAds) {
		this.deliveredProductAds = deliveredProductAds;
	}
	public ArrayList<Ad> getFavoriteAds() {
		return favoriteAds;
	}
	public void setFavoriteAds(ArrayList<Ad> favoriteAds) {
		this.favoriteAds = favoriteAds;
	}

	@Override
	public String toString() {
		return "Buyer";
	}
	
	
}
