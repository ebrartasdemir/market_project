package com.market_p.market_p.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.market_p.market_p.dto.CategoryResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.mapper.CategoryMapper;
import com.market_p.market_p.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResDto> getAllCategories() {
        List<Category> categoryList= categoryRepository.findAll();
        return categoryMapper.categoryListToCategoryResDtoList(categoryList);
    }

    @Override
    public CategoryResDto getCategoryById(int id) {
        Optional<Category> optionalCategory=categoryRepository.findById(id);
        Category category= optionalCategory.orElse(null);
        return categoryMapper.categoryToCategoryResDto(category);
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(int id, Category newCategory) {
        Optional<Category> optCategory= categoryRepository.findById(id);
        if(optCategory.isPresent()) {
            String newName=newCategory.getName();
            String newDescription=newCategory.getDescription();
            Category category = optCategory.get();
            if (newName != null) category.setName(newName);
            if (newDescription != null) category.setDescription(newDescription);
            categoryRepository.save(category);
        }
    }

    @Override
    public void updatePartialyCategory(int id, Map<String, Object> patchPayload) {
        Optional<Category> optCategory= categoryRepository.findById(id);
        if(optCategory.isEmpty()){
             throw new RuntimeException("Category not found");
        }
        if(patchPayload.containsKey("id")){
            throw new RuntimeException("Category id cannot be updated");
        }
        Category updatedCategory = apply(optCategory.get(),patchPayload);
        categoryRepository.save(updatedCategory);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);

    }
    private Category apply( Category category,Map<String,Object> partialPayload){
        ObjectNode categoryNode=objectMapper.convertValue(category , ObjectNode.class);
        ObjectNode bodyObjectNode=objectMapper.convertValue(partialPayload , ObjectNode.class);
        categoryNode.setAll(bodyObjectNode);
        return objectMapper.convertValue(categoryNode,Category.class);
    }
}
