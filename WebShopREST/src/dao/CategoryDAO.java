package dao;

import java.util.HashMap;

import beans.Category;

public class CategoryDAO {
	
	private HashMap<String, Category> categories = new HashMap<String, Category>();
	
	public CategoryDAO() {
		categories.put("Women's Clothing", new Category("Women's Clothing"));
		categories.put("Man's Clothing", new Category("Man's clothing"));
		categories.put("Mobile Phones", new Category("Mobile Phones"));
		categories.put("Computers", new Category("Computers"));
		categories.put("Watches", new Category("Watches"));
	}

	public HashMap<String, Category> getCategories() {
		return categories;
	}

	public void setCategories(HashMap<String, Category> categories) {
		this.categories = categories;
	}
	
}
