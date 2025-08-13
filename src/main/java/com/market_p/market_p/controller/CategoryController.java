package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.CategoryResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResDto>>> getAllCategories() {
        try{
            List<CategoryResDto>categoryList=categoryService.getAllCategories();
            ApiResponse<List<CategoryResDto>> apiResponse = new ApiResponse<>("All categories listed successfully.",categoryList);
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<CategoryResDto>> apiResponse= new ApiResponse<>("Categories cannot be listed, something went wrong.\n"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/category/{id}")
        public ResponseEntity<ApiResponse<CategoryResDto>> getCategoryById(@PathVariable @Min(1) int  id) {
        try{
            CategoryResDto category=categoryService.getCategoryById(id);
            String message="Category id: "+id+" found succesfully.";
            if(category==null) message="Cannot find category with id: "+id;
            ApiResponse<CategoryResDto> apiResponse = new ApiResponse<>(message,category);
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<CategoryResDto> apiResponse= new ApiResponse<>("Category cannot be found. Something went wrong.\n"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/category")
    public ResponseEntity<ApiResponse<String>> addCategory(@Valid @RequestBody Category category){
        try{
        categoryService.createCategory(category);
            ApiResponse<String> apiResponse = new ApiResponse<>("Category created successfully");
            return  ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>("Category cannot be created.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/category/{id}")
    public ResponseEntity<ApiResponse<String>> updateCategory(@Valid @PathVariable @Min(1) int id, @RequestBody Category category){
        try{
            categoryService.updateCategory(id,category);
            ApiResponse<String> apiResponse = new ApiResponse<>("Category updated successfully");
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>("Category cannot be updated. Something went wrong.\n"+e.getMessage());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    //depends on unit test
//    @PatchMapping("/category/{id}")
//    public ResponseEntity<String> updatePartialyCategory(@Valid @PathVariable @Min(1) int id, @RequestBody Map<String,Object> patchPayload){
//        try{
//            categoryService.updatePartialyCategory(id,patchPayload);
//            return  ResponseEntity.ok("Category updated successfully");
//        }
//        catch(Exception e) {
//            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category cannot be updated. Something went wrong.\n"+e.getMessage());
//        }
//    }
    @DeleteMapping("/category/{id}")
    //Cannot delete or update a parent row: a foreign key constraint fails
    public ResponseEntity<String> deleteCategory(@PathVariable @Min(1) int id){
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category cannot be deleted. Something went wrong.\n"+e.getMessage());
        }
    }
}
