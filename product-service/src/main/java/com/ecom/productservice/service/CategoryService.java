package com.ecom.productservice.service;

import com.ecom.productservice.entities.Category;
import com.ecom.productservice.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        return categoryRepository.findById(categoryId)
                .map(existingCategory -> {
                    // Update fields based on your requirements
                    existingCategory.setName(updatedCategory.getName());


                    // Save and return the updated product
                    return categoryRepository.save(updatedCategory);
                })
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + categoryId + " not found"));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
