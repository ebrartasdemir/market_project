package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.CategoryResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;

import static com.market_p.market_p.utils.Jsonify.toJson;


@RestController
@RequestMapping("/api")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;



    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResDto>>> getAllCategories() {
        logger.info("[CategoryController] Api: GET=> /categories /------------");
        try{
            List<CategoryResDto>categoryList=categoryService.getAllCategories();
            ApiResponse<List<CategoryResDto>> apiResponse = new ApiResponse<>(Messages.Category.RECORDS_FOUND_AND_LISTED,categoryList);
            logger.info("[CategoryController] Response Body: {}", toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<List<CategoryResDto>> apiResponse= new ApiResponse<>(Messages.Category.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            logger.info("[CategoryController] Error Response Body: {}",toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/category/{id}")
        public ResponseEntity<ApiResponse<CategoryResDto>> getCategoryById(@PathVariable @Min(1) int  id) {
        logger.info("[CategoryController] Api: GET=> /category/{} /------------", id);
        try{
            CategoryResDto category=categoryService.getCategoryById(id);
            String message=String.format(Messages.Category.RECORD_FOUND, id);
            if(category==null) message=String.format(Messages.Category.RECORD_NOT_FOUND, id);
            ApiResponse<CategoryResDto> apiResponse = new ApiResponse<>(message,category);
            logger.info("[CategoryController] Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<CategoryResDto> apiResponse= new ApiResponse<>(String.format(Messages.Category.RECORD_NOT_FOUND_ERROR, id)+e.getMessage());
            logger.info("[CategoryController] Error Response Body: {}",  toJson(apiResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/category")
    public ResponseEntity<ApiResponse<String>> addCategory(@Valid @RequestBody Category category){
        logger.info("[CategoryController] Api: POST=> /category /------------");
        logger.info("[CategoryController] Request Body: {}", category);
        try{
        categoryService.createCategory(category);
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_CREATED);
            logger.info("[CategoryController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_CREATED_ERROR +e.getMessage());
            logger.error("[CategoryController] Error Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/category/{id}")
    public ResponseEntity<ApiResponse<String>> updateCategory(@Valid @PathVariable @Min(1) int id, @RequestBody Category category){
        logger.info("[CategoryController] Api: PUT=> /category/{} /------------", id);
        logger.info("[CategoryController] Request Body: {}",category );
        try{
            categoryService.updateCategory(id,category);
            String message=String.format(Messages.Category.RECORD_UPDATED,id);
            if(category==null) message=String.format(Messages.Category.RECORD_NOT_FOUND,id);
            ApiResponse<String> apiResponse = new ApiResponse<>(message);
            logger.info("[CategoryController] Response Body: {}",  toJson(apiResponse));
            return  ResponseEntity.ok(apiResponse);
        }
        catch(Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Category.RECORD_UPDATED_ERROR +e.getMessage());
            logger.error("[CategoryController] Error Response Body: {}", toJson(apiResponse));
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @DeleteMapping("/category/{id}")
    //Cannot delete or update a parent row: a foreign key constraint fails
    public ResponseEntity<String> deleteCategory(@PathVariable @Min(1) int id){
        logger.info("[CategoryController] Api: DELETE=> /category/{} /------------", id);
        try{
            categoryService.deleteCategory(id);
            logger.info("[CategoryController] Response Body: {}",  toJson(HttpStatus.NO_CONTENT));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e) {
            logger.error("[CategoryController] Error Response Body: {}", Messages.Category.RECORD_DELETED_ERROR +e.getMessage());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Messages.Category.RECORD_DELETED_ERROR +e.getMessage());
        }
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
