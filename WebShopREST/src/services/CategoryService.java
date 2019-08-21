package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Category;
import dao.CategoryDAO;

@Path("")
public class CategoryService {
	@Context
	ServletContext context;
	
	@PostConstruct
	public void init() {
		if(context.getAttribute("categoryDAO") == null) {
			context.setAttribute("categoryDAO", new CategoryDAO());
		}
	}
	
	@GET
	@Path("/categories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Category> getCategoryNames(@Context HttpServletRequest request)
	{
		CategoryDAO categories = (CategoryDAO) context.getAttribute("categoryDAO");
		return categories.getCategories().values();
	}
}
