package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Ad;
import dao.AdDAO;



@Path("")
public class AdService {
	@Context
	ServletContext context;
	
	@PostConstruct
	public void init() {
		if(context.getAttribute("AdDAO") == null) {
			context.setAttribute("AdDAO", new AdDAO());
		}
	}
	
	@GET
	@Path("/ads")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ad> getAds(@Context HttpServletRequest request){
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");
		return ads.getAds().values();
	}
	
	@GET
	@Path("/favorite-ads")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Ad> getFavoriteAds(@Context HttpServletRequest request){
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");
		return ads.get10FavoriteAds();
	}
	
	@GET
	@Path("/ad/{adName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Ad getAdInfo(@PathParam("adName") String adName, @Context HttpServletRequest request){
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");
		return ads.getAds().get(adName);
	}
	
}
