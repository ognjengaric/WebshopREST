package services;


import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Ad;
import beans.Ad.Status;
import beans.Buyer;
import beans.Message;
import beans.Seller;
import beans.User;
import dao.AdDAO;
import dao.MessageDAO;
import dao.UserDAO;
import helpers.UUIDGenerator;

@Path("")
public class UserService {
	@Context
	ServletContext context;
	
	@PostConstruct
	public void init() {
		if (context.getAttribute("UserDAO") == null) {	    
			context.setAttribute("UserDAO", new UserDAO());
		}
	}
		
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logIn(User u, @Context HttpServletRequest request)
	{			
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");

		User user = users.find(u);

		if(user == null)
		{
			return Response.status(400).build();
		}

		String accessToken = UUIDGenerator.getUUID();
		user.setAccessToken(accessToken);
		
		context.setAttribute("UserDAO", users);	
		
		return Response.ok(accessToken).build();			  
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void logOut(String accessToken,@Context HttpServletRequest request) {		
		
		UserDAO users = (UserDAO) context.getAttribute("UserDAO"); 		
		
		User user = users.findBySessionID(accessToken);
		user.setAccessToken("");
		  
		context.setAttribute("UserDAO", users);
	}
	
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User u, @Context HttpServletRequest request) 
	{
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");

		if(users.getUsers().containsKey(u.getUsername())) {
			return Response.status(400).build();
		}
		
		users.getUsers().put(u.getUsername(), u);
		
		context.setAttribute("UserDAO", users);
		
		return Response.ok().build();	
	}
	
	@POST  //client does not send body data when get is used + sending token in body for safety reasons, not in URL
	@Path("/user_data") //get data by access token - used for fetching data of logged in user
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDataByToken(String accessToken,  @Context HttpServletRequest request){
		
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		
		User user = users.findBySessionID(accessToken);
		
		if(user == null)
		{
			return Response.status(400).build();
		}
		
		return Response.ok(user).build();
	}
	
	@GET
	@Path("/user_data/{username}")	//get data by username
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDataByName(@PathParam ("username") String username, @Context HttpServletRequest request) {
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		
		User user = users.getUsers().get(username);
		
		if(user == null) {
			Response.status(400).build();
		}
		
		return Response.ok(user).build();
	}
	
	
	@POST
	@Path("/make_favorite/{adName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFavoriteAd(@PathParam("adName") String adName, String accessToken, @Context HttpServletRequest request) {
		
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		AdDAO ads = (AdDAO)  context.getAttribute("AdDAO");
		
		Ad ad =  ads.getAds().get(adName);
		
		ad.setInFavoriteLists(ad.getInFavoriteLists()+1);
		
		User user = users.findBySessionID(accessToken);
		((Buyer)user.getRole()).getFavoriteAds().add(ad.getName());
		
		context.setAttribute("UserDAO", users);
		context.setAttribute("AdDAO", ads);
		
		return Response.ok().build();	
	}
	
	@POST
	@Path("/remove_favorite/{adName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeFavoriteAd(@PathParam("adName") String adName, String accessToken, @Context HttpServletRequest request) {
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");		
		AdDAO ads = (AdDAO)  context.getAttribute("AdDAO");
		
		Ad ad =  ads.getAds().get(adName);
		
		ad.setInFavoriteLists(ad.getInFavoriteLists()-1);
		
		
		User user = users.findBySessionID(accessToken);
		
		Buyer b = (Buyer)user.getRole();
	

		b.getFavoriteAds().remove(adName);
		
		context.setAttribute("UserDAO", users);
		context.setAttribute("AdDAO", ads);
		
		return Response.ok(user).build();	
	}
	
	@POST
	@Path("/order/{adName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response orderProduct(@PathParam("adName") String adName, String accessToken, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("UserDAO");		
		AdDAO ads = (AdDAO)  context.getAttribute("AdDAO");
		
		
		//Set ad status to pending
		Ad ad =  ads.getAds().get(adName);
		ad.setStatus(Status.PENDING);
		
		User user = users.findBySessionID(accessToken);
		Buyer b = (Buyer)user.getRole();
		//Insert ad to buyer ordered list
		b.getOrderedProductAds().add(adName);	
		

		Seller s = (Seller)users.getUsers().get(ad.getSellerName()).getRole();
		//Insert to seller pending ads
		s.getPendingProductAds().add(adName);		
		//Remove ad from seller published ads
		s.getPublishedAds().remove(adName);
		

		
		context.setAttribute("UserDAO", users);
		context.setAttribute("AdDAO", ads);
		
		return Response.ok(user).build();	
	}
	
	@POST
	@Path("/mark_delivered/{adName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response markAsDelivered(@PathParam("adName") String adName, String accessToken, @Context HttpServletRequest request) {

		UserDAO users = (UserDAO) context.getAttribute("UserDAO");		
		AdDAO ads = (AdDAO)  context.getAttribute("AdDAO");
		MessageDAO messages = (MessageDAO) context.getAttribute("MessageDAO");
		
		
		//Set ad status to published - to be available for ordering again 
		Ad ad =  ads.getAds().get(adName);
		ad.setStatus(Status.DELIVERED);
		
		User user = users.findBySessionID(accessToken);
		Buyer b = (Buyer)user.getRole();
		
		//Insert ad to buyer delivered list
		b.getDeliveredProductAds().add(adName);	
		//Remove from buyers ordered list
		b.getOrderedProductAds().remove(adName);
		
		Seller s = (Seller)users.getUsers().get(ad.getSellerName()).getRole();
		//Remove from seller pending ads
		s.getPendingProductAds().remove(adName);
		//Add to seller delivered ads
		s.getDeliveredProductAds().add(adName);
		//Add to seller published ads
		s.getPublishedAds().add(adName);
		
		
		 String msgContent = Message.successfullyDelivered(adName, user.getUsername());
		 Message msg = new Message("NOTIFICATIONS", ad.getSellerName(), "Product delivered", msgContent);
		 messages.getMessages().put(msg.getId(), msg);
		
		context.setAttribute("UserDAO", users);
		context.setAttribute("AdDAO", ads);
		context.setAttribute("MessageDAO", messages);
		
		return Response.ok().build();	
	}
	
	@GET
	@Path("/get_users")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsers(@Context HttpServletRequest request){
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");		
		return users.getUsers().values();
	}
	
	@POST
	@Path("/set_role/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeRole(@PathParam("username") String username, String roleName, @Context HttpServletRequest request){
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		
		User u = users.getUsers().get(username);
		
		
		if(roleName.equals("Buyer")) {
			
			Buyer b = (Buyer)u.getRole();
			
			if(!b.getDeliveredProductAds().isEmpty() || !b.getOrderedProductAds().isEmpty() || !b.getFavoriteAds().isEmpty()) {
				return Response.status(400).build();
			}
			
			u.setRole(new Seller());
		} 
		if(roleName.equals("Seller")) {
			
			Seller s = (Seller)u.getRole();
			
			if(!s.getDeliveredProductAds().isEmpty() || !s.getPendingProductAds().isEmpty() || !s.getPublishedAds().isEmpty()) {
				return Response.status(400).build();
			}
			
			u.setRole(new Buyer());
		}	
		
		context.setAttribute("UserDAO", users);
		
		return Response.ok().build();
	}
	
	  @POST	  
	  @Path("/like_profile/{username}")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response likeSellerProfile(@PathParam("username") String username, @Context HttpServletRequest request){
		  UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		  
		  User user = users.getUsers().get(username);
		  
		  if(user.getRole() instanceof Seller) {
			  Seller s  = (Seller)user.getRole();
			  s.setNumberOfLikes(s.getNumberOfLikes() + 1);
			  
			  return Response.ok().build();
		  }
		  
		  context.setAttribute("UserDAO", users);
		  
		  return Response.status(400).build();		  
	  }
	  
	  @POST	  
	  @Path("/dislike_profile/{username}")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response dislikeSellerProfile(@PathParam("username") String username, @Context HttpServletRequest request){
		  UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		  
		  User user = users.getUsers().get(username);
		  
		  if(user.getRole() instanceof Seller) {
			  Seller s  = (Seller)user.getRole();
			  s.setNumberOfDislikes(s.getNumberOfDislikes() + 1);
			  
			  return Response.ok().build();
		  }
		  
		  context.setAttribute("UserDAO", users);
		  
		  return Response.status(400).build();		  
	  }
	  
	  
	  @POST	  
	  @Path("/reset_warning_count/{username}")	  
	  @Consumes(MediaType.APPLICATION_JSON)	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response resetWarningCount(@PathParam("username") String username, @Context HttpServletRequest request){
		  UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		  
		  Seller s =(Seller)users.getUsers().get(username).getRole();
		  s.setNumberOfWarnings(0);
		  
		  context.setAttribute("UserDAO", users);
		  return Response.ok().build();
	  }
	  
	
	
}
