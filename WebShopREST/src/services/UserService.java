package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.User;
import dao.UserDAO;

@Path("")
public class UserService {
	@Context
	ServletContext context;
	
	@PostConstruct
	public void init() {
		if (context.getAttribute("userDAO") == null) {	    
			context.setAttribute("userDAO", new UserDAO());
		}
	}
	
	
	@POST
	@Path("/test")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response test(User u,@Context HttpServletRequest request)
	{			
		System.out.println(u.toString());		
		
		return Response.ok().build();			  
	}
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logIn(User u, @Context HttpServletRequest request)
	{			
		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		
		User user = users.find(u);
		
		if(user == null)
		{
			return Response.status(400).build();
		}
		
		context.setAttribute("userDAO", users);
		
		request.getSession().setAttribute("user", user);
		
		System.out.println(request.getSession().getAttribute("user"));
		
		return Response.ok().build();			  
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User u, @Context HttpServletRequest request) 
	{
		UserDAO users = (UserDAO) context.getAttribute("userDAO");
		
		if(users.getUsers().containsKey(u.getUsername())) {
			return Response.status(400).build();
		}
		
		users.getUsers().put(u.getUsername(), u);
		context.setAttribute("userDAO", users);
		
		return Response.ok().build();	
	}
	
	
}
