package services;

import java.util.ArrayList;
import java.util.Collection;
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
import beans.Message;
import beans.Review;
import beans.Seller;
import beans.User;
import beans.Ad.Status;
import dao.AdDAO;
import dao.CategoryDAO;
import dao.MessageDAO;
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
		if(context.getAttribute("MessageDAO") == null) {
			context.setAttribute("MessageDAO", new MessageDAO());
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
		  MessageDAO messages = (MessageDAO)context.getAttribute("MessageDAO");
		  
		  Ad ad = ads.getAds().get(adName);
		  
		  
		  //if ad is delivered or pending, it cannot be deleted
		  if(ad.getStatus() == Status.DELIVERED || ad.getStatus() == Status.PENDING) {
			  return Response.status(400).build();
		  }
		  
		  
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
		  
		  String msgContent = Message.adAction(adName, ad.getStatus(), users.getUsers().get(ad.getSellerName()).getUsername(), "Seller", "deleted");
		  Message msg = new Message("NOTIFICATIONS", "admin", "Ad deleted", msgContent);
		  messages.getMessages().put(msg.getId(), msg);
		  
		  //set status to deleted
		  ad.setStatus(Status.DELETED);
		  
		  context.setAttribute("UserDAO", users); 
		  context.setAttribute("AdDAO", ads);
		  context.setAttribute("CategoryDAO", categories);
		  context.setAttribute("MessageDAO", messages);
		  
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
		  MessageDAO messages = (MessageDAO)context.getAttribute("MessageDAO");
		  
		  Ad ad = ads.getAds().get(adName);
		  
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
		  
		  String msgContent = Message.adAction(adName, ad.getStatus(), "admin", "Administrator", "deleted");
		  Message msg = new Message("NOTIFICATIONS", ad.getSellerName(), "Ad deleted", msgContent);
		  messages.getMessages().put(msg.getId(), msg);
		  
		  
		  //set status deleted		  
		  ad.setStatus(Status.DELETED);
		  
		  context.setAttribute("UserDAO", users); 
		  context.setAttribute("AdDAO", ads);
		  context.setAttribute("CategoryDAO", categories);
		  context.setAttribute("MessageDAO", messages);
	  
		  return Response.ok().build(); 
	  }
	  
	  @POST	  
	  @Path("/edit_ad_admin")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response editAdAdmin(Ad ad, @Context HttpServletRequest request) {
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  MessageDAO messages = (MessageDAO)context.getAttribute("MessageDAO");
		  
		  //set original posting date
		  ad.setPostingDate(ads.getAds().get(ad.getName()).getPostingDate());
		  //set number of favorite lists that this ad is in
		  ad.setInFavoriteLists(ads.getAds().get(ad.getName()).getInFavoriteLists());
		  
		  //replace ad value 
		  ads.getAds().put(ad.getName(), ad);
		  
		  String msgContent = Message.adAction(ad.getName(), ad.getStatus(), "admin", "Administrator", "deleted");
		  Message msg = new Message("NOTIFICATIONS", ad.getSellerName(), "Ad deleted", msgContent);
		  messages.getMessages().put(msg.getId(), msg);
		  
		  context.setAttribute("AdDAO", ads);
		  context.setAttribute("MessageDAO", messages);
		  
		  return Response.ok().build();
	  }
	  
	  @POST	  
	  @Path("/like_ad/{adName}")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response likeAd(@PathParam("adName") String adName, @Context HttpServletRequest request) {
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  
		  Ad ad = ads.getAds().get(adName);
		  ad.setNumberOfLikes(ad.getNumberOfLikes() + 1);
		  
		  context.setAttribute("AdDAO", ads);
		  
		  return Response.ok().build();
	  } 
	  
	  @POST	  
	  @Path("/dislike_ad/{adName}")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response dislikeAd(@PathParam("adName") String adName, @Context HttpServletRequest request) {
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  
		  Ad ad = ads.getAds().get(adName);
		  ad.setNumberOfDislikes(ad.getNumberOfDislikes() + 1);
		  
		  context.setAttribute("AdDAO", ads);
		  
		  return Response.ok().build();
	  }
	  
	  @GET
	  @Path("/get_review/{id}")
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response getReviewInfo(@PathParam ("id") String reviewId, @Context HttpServletRequest request) {
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  
		  for (Ad ad : ads.getAds().values()) {
			for (Review review : ad.getReviews()) {
				if(review.getId().equals(reviewId))
					return Response.ok(review).build();
			}
		}
		  
		 return Response.status(400).build();
	  }
	  
	  @POST	  
	  @Path("/post_review")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response postReview(Review review, @Context HttpServletRequest request) {
		  AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		  MessageDAO messages = (MessageDAO)context.getAttribute("MessageDAO");
		  UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		  
		  Ad ad = ads.getAds().get(review.getAdName());  
		  
		  ad.getReviews().add(review);
		   
		  Message msg1 = new Message("NOTIFICATIONS", ad.getSellerName(), "Review added", Message.reviewAction(review.getTitle(), "added", review.getAdName(), review.getAuthor()));
		  messages.getMessages().put(msg1.getId(), msg1);
		  
		  Seller s = (Seller) users.getUsers().get(ad.getSellerName()).getRole();
		  
		  if(!review.getIsDescriptionAccurate()) {
			  Message msg2 = new Message("NOTIFICATIONS", ad.getSellerName(), "Ad does not contain correct description!", Message.descriptionNotAccurate(review.getAdName(), review.getAuthor()));
			  messages.getMessages().put(msg2.getId(), msg2);
			 
			 s.setNumberOfWarnings(s.getNumberOfWarnings() + 1);
		  }
		  
		  if(!review.getIsDealFulfilled()) {
			  Message msg3 = new Message("NOTIFICATIONS", ad.getSellerName(), "Deal has not been fullfilled", Message.dealNotFullfilled(review.getAuthor(), review.getAdName()));
			  messages.getMessages().put(msg3.getId(), msg3);
			  
			  s.setNumberOfWarnings(s.getNumberOfWarnings() + 1);
		  }
		  
		  
		  context.setAttribute("AdDAO", ads);
		  context.setAttribute("MessageDAO", messages);
		  context.setAttribute("UserDAO", users);
		  
		  return Response.ok().build();
	  }
	  
	@POST
	@Path("/edit_review")
	@Consumes(MediaType.APPLICATION_JSON)	  
	@Produces(MediaType.APPLICATION_JSON)
	public Response editReview(Review review, @Context HttpServletRequest request) {
		AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		MessageDAO messages = (MessageDAO)context.getAttribute("MessageDAO");
		String prevTitle = "";
		
		for(Ad ad : ads.getAds().values()) {
			for(Review r : ad.getReviews()) {
				if(r.getId().equals(review.getId())) {
					prevTitle = r.getTitle();
					ad.getReviews().remove(r);
					ad.getReviews().add(review);
				}
			}
		}
		
		 String msgContent = Message.reviewAction(prevTitle, "edited", review.getAdName(), review.getAuthor());
		 Message msg = new Message("NOTIFICATIONS", ads.getAds().get(review.getAdName()).getSellerName(), "Review edited", msgContent);
		 messages.getMessages().put(msg.getId(), msg);
		
		context.setAttribute("MessageDAO", messages);
		context.setAttribute("AdDAO", ads);
		
		return Response.ok().build();
	}
	  
	@DELETE
	@Path("/delete_review/{id}")
	@Consumes(MediaType.APPLICATION_JSON)	  
	@Produces(MediaType.APPLICATION_JSON) 
	public Response deleteReview(@PathParam("id") String reviewId, @Context HttpServletRequest request) {
		AdDAO ads = (AdDAO)context.getAttribute("AdDAO");
		
		for (Ad ad : ads.getAds().values()) {
			if(ad.getReviews().stream().anyMatch(review -> review.getId().equals(reviewId))) {
				ad.getReviews().stream().forEach(review -> {
					if(review.getId().equals(reviewId)) {
						review.setIsActive(false);
					}
				});
			}
		}
		
		context.setAttribute("AdDAO", ads);
		return Response.ok().build();
	}
}
