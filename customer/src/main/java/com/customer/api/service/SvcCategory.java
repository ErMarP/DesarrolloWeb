package com.customer.api.service;

import java.util.List;

import com.customer.api.dto.ApiResponse;
import com.customer.api.entity.Category;

public interface SvcCategory {

	List<Category> getCategories();
	Category getCategory(Integer category_id);
	ApiResponse createCategory(Category category);
	ApiResponse updateCategory(Integer category_id, Category category);
	ApiResponse deleteCategory(Integer category_id);
	
}
