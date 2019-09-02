package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Ad;
import beans.Seller;
import dao.AdDAO;
import dao.UserDAO;



@Path("")
public class AdService {
	@Context
	ServletContext context;
	
	@PostConstruct
	public void init() {
		if(context.getAttribute("AdDAO") == null) {
			context.setAttribute("AdDAO", new AdDAO());
		}
		if(context.getAttribute("UserDAO") == null) {
			context.setAttribute("UserDAO", new UserDAO());
		}
	}
	
	@GET
	@Path("/ads")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ad> getAds(@Context HttpServletRequest request){
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");
		return ads.getPublishableAds();
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
	
	@POST
	@Path("/post-ad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAd(Ad ad, @Context HttpServletRequest request){
		UserDAO users = (UserDAO) context.getAttribute("UserDAO"); 		
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");
		
		if(ads.getAds().containsKey(ad.getName())) {
			return Response.status(400).build();
		}
		
		//add to seller orders list
		((Seller)users.getUsers().get(ad.getSellerName()).getRole()).getPublishedAds().add(ad);
		
		//add to AdDAO hashmap
		ads.getAds().put(ad.getName(), ad);
		
		context.setAttribute("UserDAO", users);
		context.setAttribute("AdDAO", ads);
		
		return Response.ok().build();
	}
	
	
	  @DELETE	  
	  @Path("/delete-ad")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response deleteAd(Ad ad, @Context HttpServletRequest request)
	  { 
		  UserDAO users = (UserDAO)context.getAttribute("UserDAO"); 
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
	  
		  if(!ads.getAds().containsKey(ad.getName())) 
		  { 
			  return Response.status(400).build(); 
		  }
		  
		  
		  context.setAttribute("UserDAO", users); 
		  context.setAttribute("AdDAO", ads);
	  
		  return Response.ok().build(); 
	  }
	 
	
	
}
