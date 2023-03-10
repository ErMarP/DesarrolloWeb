package com.customer.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.api.entity.Category;
import com.customer.api.repository.RepoCategory;

@Service
public class SvcCategoryImp implements SvcCategory {

	@Autowired	
	RepoCategory repo;
	
	@Override
	public List<Category> getCategories() {
		return repo.findByStatus(1);
	}

	@Override
	public Category getCategory(Integer category_id) {
		return repo.findByCategoryId(category_id);
	}

	@Override
	public String createCategory(Category category) {
		Category categorySaved = (Category) repo.findByCategory(category.getCategory());
		if (categorySaved != null) {
			if (categorySaved.getStatus() == 0) {					
				repo.activateCategory(categorySaved.getCategory_id());
				return "category has been ativated";
			}else {
				return "category already exists";
			}
		}
		repo.createCategory(category.getCategory(), category.getAcronym());
		return "category created";
	}

	@Override
	public String updateCategory(Integer category_id, Category category) {
		Category categorySaved = (Category) repo.findByCategoryId(category_id);
		
		if (categorySaved == null) {
			return "category does not exist";
		}else {
			if (categorySaved.getStatus() == 0) {					
				return "region is not active";
			}else {
				categorySaved = (Category) repo.findByCategory(category.getCategory());
				if (categorySaved != null) {
					return "category already exists";
				}
				repo.updateCategory(category_id, category.getCategory());
				return "category updated";
			}
		}
	}

	@Override
	public String deleteCategory(Integer category_id) {
		Category categorySaved = (Category) repo.findByCategoryId(category_id);
		
		if (categorySaved == null) {
			return "category does not exist";
		}else {
			repo.deleteCategoryById(category_id);
			return "category removed";
		}
	}
}
