package com.eshop.eshop.service.category;

import com.eshop.eshop.model.Category;
import com.eshop.eshop.model.Product;

import java.util.List;

public interface ICategoryService {
Category getCategoryById(Long categoryId);
Category getCategoryByName(String name);
List<Category> getAllCategories();
Category addCategory(Category category);
Category updateCategory(Category category, Long id);
void deleteCategoryById(Long id);

}
