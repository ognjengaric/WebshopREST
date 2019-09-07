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
import beans.Buyer;
import beans.Category;
import beans.Seller;
import beans.User;
import beans.Ad.Status;
import dao.AdDAO;
import dao.CategoryDAO;
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
		if(context.getAttribute("CategoryDAO") == null) {
			context.setAttribute("CategoryDAO", new CategoryDAO());
		}
	}
	
	@GET
	@Path("/ads")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ad> getAds(@Context HttpServletRequest request){
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");		
		return ads.publishableAds();
	}
	
	@GET
	@Path("/favorite-ads")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Ad> getFavoriteAds(@Context HttpServletRequest request){
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");
		return ads.favoriteAds();
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
		
		//add to seller published list
		((Seller)users.getUsers().get(ad.getSellerName()).getRole()).getPublishedAds().add(ad);
		
		System.out.println((Seller)users.getUsers().get(ad.getSellerName()).getRole());
		
		//add to AdDAO hashmap
		ads.getAds().put(ad.getName(), ad);
		
		context.setAttribute("UserDAO", users);
		context.setAttribute("AdDAO", ads);
		
		return Response.ok().build();
	}
	
	
	  @DELETE	  
	  @Path("/delete-ad/{adName}")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response deleteAd(@PathParam("adName") String adName, @Context HttpServletRequest request)
	  { 
		  UserDAO users = (UserDAO)context.getAttribute("UserDAO"); 
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  CategoryDAO categories = (CategoryDAO)context.getAttribute("CategoryDAO");
		  
		  Ad ad = ads.getAds().get(adName);
		  
		  
		  //if seller has this ad in delivered items, it cannot be deleted
		  if(((Seller)users.getUsers().get(ad.getSellerName()).getRole()).getDeliveredProductAds().contains(ad)) {
			  return Response.status(400).build();
		  }
		  
		  //set status to deleted
		  ads.getAds().get(adName).setStatus(Status.DELETED);
		  
		  
		  //remove from seller published list
		  ((Seller)users.getUsers().get(ad.getSellerName()).getRole()).getPublishedAds().remove(ad);
		  
		  
		  //remove if in any user favorite list
		  for(User u : users.getUsers().values()) {
			  
			  if(u.getRole() instanceof Buyer) {
				  
				  Buyer b = (Buyer) u.getRole();
				  
				  if(b.getFavoriteAds().contains(ad)) {
					  b.getFavoriteAds().remove(ad);
				  }
			  }			  

		  }		  
		  
		  //remove if in certain category
		  for(Category c : categories.getCategories().values()) {
			  if(c.getAds().contains(ad)) {
				  c.getAds().remove(ad);
			  }
		  }
		  
		  context.setAttribute("UserDAO", users); 
		  context.setAttribute("AdDAO", ads);
	  
		  return Response.ok().build(); 
	  }
	 
	  
	
}
