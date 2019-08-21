package dao;

import java.util.HashMap;

import beans.Category;

public class CategoryDAO {
	
	private HashMap<String, Category> categories = new HashMap<String, Category>();
	
	public CategoryDAO() {
		categories.put("Zenska odeca", new Category("Zenska odeca"));
		categories.put("Muska odeca", new Category("Muska odeca"));
		categories.put("Telefoni", new Category("Telefoni"));
		categories.put("Racunari", new Category("Racunari"));
		categories.put("Satovi", new Category("Satovi"));
	}

	public HashMap<String, Category> getCategories() {
		return categories;
	}

	public void setCategories(HashMap<String, Category> categories) {
		this.categories = categories;
	}
	
}
