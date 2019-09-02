package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import beans.Ad;
import beans.Ad.Status;

public class AdDAO {
	private HashMap<String, Ad> ads = new HashMap<String, Ad>();

	public AdDAO() {
	}
	
	
	public HashMap<String, Ad> getAds() {
		return ads;
	}

	public void setAds(HashMap<String, Ad> ads) {
		this.ads = ads;
	}
	
	public ArrayList<Ad> get10FavoriteAds(){
		 
		ArrayList<Ad> favoriteAds = new ArrayList<Ad>(this.getPublishableAds());		
		Collections.sort(favoriteAds);
		
		if(!(favoriteAds.size() < 10)) {
			favoriteAds.subList(10, favoriteAds.size()).clear();
		}	
		
		return favoriteAds;
		
	}
	
	//ads to be published
	public ArrayList<Ad> getPublishableAds(){
		ArrayList<Ad> retList = new ArrayList<Ad>();
		
		for(Ad ad : this.ads.values()) {
			if(ad.getStatus() == Status.PUBLISHED) {
				retList.add(ad);
			}
		}
		
		return retList;
	}
	
	public static Ad findAdInList(ArrayList<Ad> list, String adName) {
		
		for(Ad ad : list) {
			if(ad.getName().equals(adName)) {
				return ad;
			}
		}
		return null;
	}
	
}
