package beans;

import java.util.List;

public class Buyer{
	
	private List<Ad> orderedProductAds;
	private List<Ad> deliveredProductAds;
	private List<Ad> favoriteAds;
	
	public Buyer() {
	}
	
	public List<Ad> getOrderedProductAds() {
		return orderedProductAds;
	}
	public void setOrderedProductAds(List<Ad> orderedProductAds) {
		this.orderedProductAds = orderedProductAds;
	}
	public List<Ad> getDeliveredProductAds() {
		return deliveredProductAds;
	}
	public void setDeliveredProductAds(List<Ad> deliveredProductAds) {
		this.deliveredProductAds = deliveredProductAds;
	}
	public List<Ad> getFavoriteAds() {
		return favoriteAds;
	}
	public void setFavoriteAds(List<Ad> favoriteAds) {
		this.favoriteAds = favoriteAds;
	}

	@Override
	public String toString() {
		return "\nRole: Buyer";
	}
	
	
}
