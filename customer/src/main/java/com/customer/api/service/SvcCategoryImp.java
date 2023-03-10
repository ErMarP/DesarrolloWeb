package com.customer.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customer.api.dto.ApiResponse;
import com.customer.api.entity.Category;
import com.customer.api.repository.RepoCategory;
import com.customer.exception.ApiException;

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
		Category category =  repo.findByCategoryId(category_id);
		if (category == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "region does not exist");
		}else {
			return category;
		}
	}

	@Override
	public ApiResponse createCategory(Category category) {
		Category categorySaved = (Category) repo.findByCategory(category.getCategory());
		if (categorySaved != null) {
			if (categorySaved.getStatus() == 0) {					
				repo.activateCategory(categorySaved.getCategory_id());
				return new ApiResponse("category has been ativated");
			}else {
				throw new ApiException(HttpStatus.BAD_REQUEST, "category already exists");
			}
		}
		repo.createCategory(category.getCategory(), category.getAcronym());
		return new ApiResponse("category created");
	}

	@Override
	public ApiResponse updateCategory(Integer category_id, Category category) {
		Category categorySaved = (Category) repo.findByCategoryId(category_id);
		
		if (categorySaved == null) {
			throw new ApiException(HttpStatus.NOT_FOUND,"category does not exist");
		}else {
			if (categorySaved.getStatus() == 0) {					
				throw new ApiException(HttpStatus.BAD_REQUEST,"region is not active");
			}else {
				categorySaved = (Category) repo.findByCategory(category.getCategory());
				if (categorySaved != null) {
					throw new ApiException(HttpStatus.BAD_REQUEST,"category already exists");
				}
				repo.updateCategory(category_id, category.getCategory());
				return new ApiResponse("category updated");
			}
		}
	}

	@Override
	public ApiResponse deleteCategory(Integer category_id) {
		Category categorySaved = (Category) repo.findByCategoryId(category_id);
		
		if (categorySaved == null) {
			throw new ApiException(HttpStatus.NOT_FOUND,"category does not exist");
		}else {
			repo.deleteCategoryById(category_id);
			return new ApiResponse("category removed");
		}
	}
}
