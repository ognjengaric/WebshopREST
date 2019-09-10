package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

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
	
	
	//Promeni u oglase koji se nalaze u najvise omiljenih
	public ArrayList<Ad> favoriteAds(){
		 
		ArrayList<Ad> favoriteAds = new ArrayList<Ad>(this.ads.values());		
		Collections.sort(favoriteAds);
		
		favoriteAds = (ArrayList<Ad>)this.ads.values().stream().filter(ad -> ad.getStatus() == Status.PUBLISHED || ad.getStatus() == Status.DELIVERED).collect(Collectors.toList());
		
		if(favoriteAds.size() > 10) {
			favoriteAds.subList(10, favoriteAds.size()).clear();
		}	
		
		return favoriteAds;
		
	}



	@Override
	public String toString() {
		String retVal = "";
		
		for(Ad ad : this.ads.values()) {
			retVal += ad.toString();
		}
		
		return retVal;
	}
	
}
