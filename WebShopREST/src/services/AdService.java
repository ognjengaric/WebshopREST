package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
	@Path("/all_ads")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ad> getAllAds(@Context HttpServletRequest request){
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");		
		return ads.getAds().values().stream().filter(ad -> ad.getStatus() != Status.DELETED).collect(Collectors.toList());
	}
	
	@GET
	@Path("/ads")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ad> getAds(@Context HttpServletRequest request){
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");		
		return ads.getAds().values().stream().filter(ad -> ad.getStatus() == Status.PUBLISHED || ad.getStatus() == Status.DELIVERED).collect(Collectors.toList());
	}
	
	@GET
	@Path("/favorite_ads")
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
	@Path("/post_ad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAd(Ad ad, @Context HttpServletRequest request){
		UserDAO users = (UserDAO) context.getAttribute("UserDAO"); 		
		AdDAO ads = (AdDAO) context.getAttribute("AdDAO");
		
		if(ads.getAds().containsKey(ad.getName())) {
			return Response.status(400).build();
		}
		
		//add to seller published list
		((Seller)users.getUsers().get(ad.getSellerName()).getRole()).getPublishedAds().add(ad.getName());
		
		//add to AdDAO hashmap
		ads.getAds().put(ad.getName(), ad);
		
		context.setAttribute("UserDAO", users);
		context.setAttribute("AdDAO", ads);
		
		return Response.ok().build();
	}
	
	
	  @DELETE	  
	  @Path("/delete_ad/{adName}")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response deleteAd(@PathParam("adName") String adName, @Context HttpServletRequest request)
	  { 
		  UserDAO users = (UserDAO)context.getAttribute("UserDAO"); 
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  CategoryDAO categories = (CategoryDAO)context.getAttribute("CategoryDAO");
		  
		  Ad ad = ads.getAds().get(adName);
		  
		  
		  //if ad is delivered or pending, it cannot be deleted
		  if(ad.getStatus() == Status.DELIVERED || ad.getStatus() == Status.PENDING) {
			  return Response.status(400).build();
		  }
		  
		  //set status to deleted
		  ad.setStatus(Status.DELETED);
		  
		  
		  //remove from seller published list
		  ((Seller)users.getUsers().get(ad.getSellerName()).getRole()).getPublishedAds().remove(adName);
		  
		  
		  //remove if in any user favorite list
		  for(User u : users.getUsers().values()) {
			  
			  if(u.getRole() instanceof Buyer) {
				  
				  Buyer b = (Buyer) u.getRole();
				  
				  if(b.getFavoriteAds().contains(adName)) {
					  b.getFavoriteAds().remove(adName);
				  }
			  }			  

		  }		  
		  
		  //remove if in certain category
		  for(Category c : categories.getCategories().values()) {
			  if(c.getAds().contains(adName)) {
				  c.getAds().remove(adName);
			  }
		  }
		  
		  context.setAttribute("UserDAO", users); 
		  context.setAttribute("AdDAO", ads);
		  context.setAttribute("CategoryDAO", categories);
	  
		  return Response.ok().build(); 
	  }
	 
	
	  @POST	  
	  @Path("/edit_ad")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response editAd(Ad ad, @Context HttpServletRequest request) {
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  
		  //delivered or pending ad cannot be edited
		  if(ad.getStatus() == Status.DELIVERED || ad.getStatus() == Status.PENDING) {
			  return Response.status(400).build();
		  }
		  
		  //set original posting date
		  ad.setPostingDate(ads.getAds().get(ad.getName()).getPostingDate());
		  //set number of favorite lists that this ad is in
		  ad.setInFavoriteLists(ads.getAds().get(ad.getName()).getInFavoriteLists());
		  
		  //replace ad value 
		  ads.getAds().put(ad.getName(), ad);
		  
		  context.setAttribute("AdDAO", ads);
		  
		  return Response.ok().build();
	  }
	  
	  
	  @DELETE	  
	  @Path("/delete_ad_admin/{adName}")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response deleteAdAdmin(@PathParam("adName") String adName, @Context HttpServletRequest request)
	  { 
		  UserDAO users = (UserDAO)context.getAttribute("UserDAO"); 
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  CategoryDAO categories = (CategoryDAO)context.getAttribute("CategoryDAO");
		  
		  Ad ad = ads.getAds().get(adName);
		  
		  //set status deleted		  
		  ad.setStatus(Status.DELETED);
		  //remove from seller published list
		  ((Seller)users.getUsers().get(ad.getSellerName()).getRole()).getPublishedAds().remove(adName);
		  ((Seller)users.getUsers().get(ad.getSellerName()).getRole()).getPendingProductAds().remove(adName);
		  ((Seller)users.getUsers().get(ad.getSellerName()).getRole()).getDeliveredProductAds().remove(adName);
		  
		  //remove from all user lists
		  for(User u : users.getUsers().values()) {
			  
			  if(u.getRole() instanceof Buyer) {
				  
				  Buyer b = (Buyer) u.getRole();
				  
				  if(b.getFavoriteAds().contains(adName)) {
					  b.getFavoriteAds().remove(adName);
				  }
				  if(b.getOrderedProductAds().contains(adName)) {
					  b.getOrderedProductAds().remove(adName);
				  }
				  if(b.getDeliveredProductAds().contains(adName)) {
					  b.getDeliveredProductAds().remove(adName);
				  }
			  }			  

		  }	  
		  
		  
		  //remove if in certain category
		  for(Category c : categories.getCategories().values()) {
			  if(c.getAds().contains(adName)) {
				  c.getAds().remove(adName);
			  }
		  }	
		  
		  context.setAttribute("UserDAO", users); 
		  context.setAttribute("AdDAO", ads);
		  context.setAttribute("CategoryDAO", categories);
	  
		  return Response.ok().build(); 
	  }
	  
	  @POST	  
	  @Path("/edit_ad_admin")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response editAdAdmin(Ad ad, @Context HttpServletRequest request) {
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  
		  //set original posting date
		  ad.setPostingDate(ads.getAds().get(ad.getName()).getPostingDate());
		  //set number of favorite lists that this ad is in
		  ad.setInFavoriteLists(ads.getAds().get(ad.getName()).getInFavoriteLists());
		  
		  //replace ad value 
		  ads.getAds().put(ad.getName(), ad);
		  
		  context.setAttribute("AdDAO", ads);
		  
		  return Response.ok().build();
	  }
	 
}
