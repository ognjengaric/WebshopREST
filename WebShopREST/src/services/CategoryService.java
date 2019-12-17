package services;

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

import beans.Category;
import dao.AdDAO;
import dao.CategoryDAO;

@Path("")
public class CategoryService {
	@Context
	ServletContext context;
	
	@PostConstruct
	public void init() {
		if(context.getAttribute("CategoryDAO") == null) {
			context.setAttribute("CategoryDAO", new CategoryDAO());
		}
	}
	
	@GET
	@Path("/categories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Category> getCategories(@Context HttpServletRequest request)
	{
		CategoryDAO categories = (CategoryDAO) context.getAttribute("CategoryDAO");
		return categories.getCategories().values().stream().filter(category -> category.isActive).collect(Collectors.toList());
	}
	
	@GET
	@Path("/category/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Category getCategory(@PathParam ("id") String id, @Context HttpServletRequest request) {
		CategoryDAO categories = (CategoryDAO) context.getAttribute("CategoryDAO");
		return categories.getCategories().get(id);
	}
	
	@POST
	@Path("/post_category")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postCategory(Category category, @Context HttpServletRequest request){
		CategoryDAO categories = (CategoryDAO) context.getAttribute("CategoryDAO");
		
		
		for (Category cat : categories.getCategories().values()) {
			if(cat.getName().equals(category.getName())) {
				return Response.status(400).build();
			}
		}
		
		categories.getCategories().put(category.getId(), category);
		context.setAttribute("CategoryDAO", categories);
		
		return Response.ok().build();
	}
	
	
	@POST
	@Path("/edit_category")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editCategory(Category category, @Context HttpServletRequest request){
		CategoryDAO categories = (CategoryDAO) context.getAttribute("CategoryDAO");
		
		for (Category cat : categories.getCategories().values()) {
			if(cat.getName().equals(category.getName()) && !cat.getId().equals(category.getId())) {
				return Response.status(400).build();
			}
		}
		
		categories.getCategories().put(category.getId(), category);
		
		context.setAttribute("CategoryDAO", categories);
		return Response.ok().build();
	}
	
	
	@DELETE
	@Path("/delete_category/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCategory(@PathParam ("id") String id, @Context HttpServletRequest request){
		CategoryDAO categories = (CategoryDAO) context.getAttribute("CategoryDAO");
		
		categories.getCategories().get(id).setIsActive(false);
		
		context.setAttribute("CategoryDAO", categories);
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/add_ad/{adName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAdToCategory(@PathParam ("adName")  String adName, String categoryId, @Context HttpServletRequest request){
		CategoryDAO categories = (CategoryDAO) context.getAttribute("CategoryDAO");
		
		categories.getCategories().get(categoryId).getAds().add(adName);
		
		context.setAttribute("CategoryDAO", categories);
		
		return Response.ok().build();
	}
	
	
	@POST
	@Path("/remove_ad/{adName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeAdFromCategory(@PathParam ("adName")  String adName, String categoryId, @Context HttpServletRequest request){
		CategoryDAO categories = (CategoryDAO) context.getAttribute("CategoryDAO");
		
		categories.getCategories().get(categoryId).getAds().remove(adName);
		
		context.setAttribute("CategoryDAO", categories);
		
		return Response.ok().build();
	}
	
 }
 
