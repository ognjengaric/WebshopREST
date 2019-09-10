package dao;

import java.util.HashMap;

import beans.Category;

public class CategoryDAO {
	
	private HashMap<String, Category> categories = new HashMap<String, Category>();
	
	public CategoryDAO() {

	}

	public HashMap<String, Category> getCategories() {
		return categories;
	}

	public void setCategories(HashMap<String, Category> categories) {
		this.categories = categories;
	}
	
}
