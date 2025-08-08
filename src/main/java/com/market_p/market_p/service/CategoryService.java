package com.market_p.market_p.service;

import com.market_p.market_p.dto.CategoryResDto;
import com.market_p.market_p.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<CategoryResDto> getAllCategories();
    CategoryResDto getCategoryById(int id);
    void createCategory(Category category);
    void updateCategory(int id,Category category);
    void updatePartialyCategory(int id, Map<String, Object> patchPayload);
    void deleteCategory(int id);
}
