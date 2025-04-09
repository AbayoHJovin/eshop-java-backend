package com.eshop.eshop.service.category;

import com.eshop.eshop.exceptions.AlreadyExistsException;
import com.eshop.eshop.exceptions.ResourceNotFoundException;
import com.eshop.eshop.model.Category;
import com.eshop.eshop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long categoryId) {
       return categoryRepository.findById(categoryId)
               .orElseThrow(()-> new ResourceNotFoundException("Category Not Found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c-> !categoryRepository.existsbyName(c.getName()))
                .map(categoryRepository :: save)
                .orElseThrow(()-> new AlreadyExistsException(category.getName()+ "Already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory->{
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()-> new ResourceNotFoundException("Category Not Found!"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository :: delete, ()-> {throw new ResourceNotFoundException("Category Not Found!");});
    }
}
