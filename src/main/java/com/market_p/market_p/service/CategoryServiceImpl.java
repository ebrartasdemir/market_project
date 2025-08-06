package com.market_p.market_p.service;

import com.market_p.market_p.entity.Category;
import com.market_p.market_p.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        Optional<Category> optionalCategory=categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(int id, Category newCategory) {
        Optional<Category> optCategory= categoryRepository.findById(id);
        String newName=newCategory.getName();
        String newDescription=newCategory.getDescription();
        if(optCategory.isPresent()) {
            Category category = optCategory.get();
            if (newName != null) category.setName(newName);
            if (newDescription != null) category.setDescription(newDescription);
            categoryRepository.save(category);
        }
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);

    }
}
