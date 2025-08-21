package com.market_p.market_p.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market_p.market_p.dto.Category.CategoryResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.mapper.CategoryMapper;
import com.market_p.market_p.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.market_p.market_p.utils.Jsonify.toJson;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    private String message;

    @Override
    public List<CategoryResDto> getAllCategories() {
        logger.info("[CategoryService] Service Method: getAllCategories - ( ) -  ------------");
        logger.info("[CategoryService] Input: null");
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResDto> categoryResDtoList=categoryMapper.categoryListToCategoryResDtoList(categoryList);
        logger.info("[CategoryService] Output: {}",categoryResDtoList);
        logger.info("[CategoryService] Listed Successfully");
        return categoryResDtoList;
    }

    @Override
    public CategoryResDto getCategoryById(int id) {
        logger.info("[CategoryService] Service Method: getCategoryById - ( id ) -  ------------",id);
        logger.info("[CategoryService] Input: id => {}",id);
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory.orElse(null);
        CategoryResDto categoryResDto=categoryMapper.categoryToCategoryResDto(category);
        logger.info("[CategoryService] Output: DTO =>{}",toJson(categoryResDto));
        logger.info("[CategoryService] Found Successfully");
        return categoryResDto;
    }

    @Override
    public void createCategory(Category category) {
        logger.info("[CategoryService] Service Method: createCategory - ( category ) - /------------");
        logger.info("[CategoryService] Input: category => {}",toJson(category));
        categoryRepository.save(category);
        logger.info("[CategoryService] Output: null");
        logger.info("[CategoryService] Created Successfully");
    }

    @Override
    public void updateCategory(int id, Category newCategory) {
        logger.info("[CategoryService] Service Method: updateCategory - ( id , category ) - /------------");
        logger.info("[CategoryService] Input : id => {} , category=> {}", id,toJson(newCategory));
        Optional<Category> optCategory = categoryRepository.findById(id);
        if (optCategory.isEmpty()){
            message=String.format(Messages.Category.RECORD_NOT_FOUND,id);
            logger.error("[CategoryService] Error: {}",message);
            logger.warn("[CategoryService] Update Failed");
            throw new RuntimeException(message);}
        else if (newCategory.getName()==null && newCategory.getDescription()==null){
            message=Messages.EMPTY_BODY;
            logger.error("[CategoryService] Error: {}",message);
            logger.warn("[CategoryService] Update Failed");
            throw new RuntimeException(message);}
         else{
            String newName = newCategory.getName();
            String newDescription = newCategory.getDescription();
            Category category = optCategory.get();
            if (newName != null) category.setName(newName);
            if (newDescription != null) category.setDescription(newDescription);
            logger.info("[CategoryService] Output: {}: null");
            categoryRepository.save(category);
            logger.info("[CategoryService] Updated Successfully");
        }
    }
    @Override
    public void deleteCategory(int id) {
        logger.info("[CategoryService] Service Method: deleteCategory - ( id ) - /------------");
        logger.info("[CategoryService] Input: id => {}",id);
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            logger.info("[CategoryService] Output: null");
            logger.info("[CategoryService] Deleted Successfully");
        }
        else {
            message=String.format(Messages.Category.RECORD_NOT_FOUND,id);
            logger.error("[CategoryService] Error: {}",message);
            logger.warn("[CategoryService] Delete Failed");
            throw new RuntimeException(message);}
    }

//    @Override
//    public void updatePartialyCategory(int id, Map<String, Object> patchPayload) {
//        Optional<Category> optCategory = categoryRepository.findById(id);
//        if (optCategory.isEmpty()) {
//            throw new RuntimeException("Category not found");
//        }
//        if (patchPayload.containsKey("id")) {
//            throw new RuntimeException("Category id cannot be updated");
//        }
//        Category updatedCategory = apply(optCategory.get(), patchPayload);
//        categoryRepository.save(updatedCategory);
//    }



//    private Category apply( Category category,Map<String,Object> partialPayload){
//        ObjectNode categoryNode=objectMapper.convertValue(category , ObjectNode.class);
//        ObjectNode bodyObjectNode=objectMapper.convertValue(partialPayload , ObjectNode.class);
//        categoryNode.setAll(bodyObjectNode);
//        return objectMapper.convertValue(categoryNode,Category.class);
//    }
}
