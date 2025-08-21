package com.market_p.market_p.mapper;

import com.market_p.market_p.dto.Category.CategoryResDto;
import com.market_p.market_p.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CategoryMapper {
    public CategoryResDto categoryToCategoryResDto(Category category){
        CategoryResDto categoryResDto = new CategoryResDto();
        categoryResDto.setName(category.getName());
        categoryResDto.setDescription(category.getDescription());
        return categoryResDto;
    }
    public Category categoryDtoToCategory(CategoryResDto categoryResDto){
        Category category = new Category();
        category.setName(categoryResDto.getName());
        category.setDescription(categoryResDto.getDescription());
        return category;
    }
    public List<CategoryResDto> categoryListToCategoryResDtoList(List<Category> categoryList){
        List<CategoryResDto> categoryResDtoList =new ArrayList<>();
        categoryList.forEach(category-> categoryResDtoList.add(categoryToCategoryResDto(category)));
        return categoryResDtoList;
    }
}
