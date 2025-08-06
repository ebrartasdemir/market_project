package com.market_p.market_p.controller;

import com.market_p.market_p.entity.Category;
import com.market_p.market_p.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @GetMapping("/category/{id}")
        public Category getCategoryById(@PathVariable int id) {
    return  categoryService.getCategoryById(id);
    }
    @PostMapping("/category")
    public void addCategory(@RequestBody Category category){
        categoryService.createCategory(category);
    }
    @PutMapping("/category/{id}")
    public void updateCategory(@PathVariable int id,@RequestBody Category category){
        categoryService.updateCategory(id,category);
    }
    @DeleteMapping("/category/{id}")//Cannot delete or update a parent row: a foreign key constraint fails
    public void deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
    }
}
