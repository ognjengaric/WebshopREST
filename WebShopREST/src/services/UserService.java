package services;

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
import beans.Buyer;
import beans.User;
import dao.AdDAO;
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
		if(context.getAttribute("AdDAO") == null) {
			context.setAttribute("AdDAO", new AdDAO());
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
	
	@GET
	@Path("/data/{accessToken}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserData(@PathParam("accessToken") String accessToken,  @Context HttpServletRequest request) {
		
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		
		User user = users.findBySessionID(accessToken);
		
		if(user == null)
		{
			return Response.status(400).build();
		}
		
		return Response.ok(user).build();	
	}
	
	
	@POST
	@Path("/make-favorite/{adName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFavoriteAd(@PathParam("adName") String adName, String accessToken, @Context HttpServletRequest request) {
		
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		AdDAO ads = (AdDAO)  context.getAttribute("AdDAO");
		
		Ad ad =  ads.getAds().get(adName);
		
		User user = users.findBySessionID(accessToken);
		((Buyer)user.getRole()).getFavoriteAds().add(ad);
		
		context.setAttribute("UserDAO", users);
		
		return Response.ok().build();	
	}
	
	@POST
	@Path("/remove-favorite/{adName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeFavoriteAd(@PathParam("adName") String adName, String accessToken, @Context HttpServletRequest request) {
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");		
		
		User user = users.findBySessionID(accessToken);
		
		Buyer b = (Buyer)user.getRole();
		b.getFavoriteAds().remove(b.findAdInList(b.getFavoriteAds(), adName));		
		
		context.setAttribute("UserDAO", users);
		
		return Response.ok(user).build();	
	}
	
	
}
