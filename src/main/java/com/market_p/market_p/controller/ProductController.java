package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.ProductReqDto;
import com.market_p.market_p.dto.ProductResDto;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductResDto>>> getAllProduct(){
        try{
            List<ProductResDto> productList=  productService.getAllProducts();
            ApiResponse<List<ProductResDto>> productApiResponse = new ApiResponse<>(Messages.Product.RECORDS_FOUND_AND_LISTED,productList);
            return ResponseEntity.ok(productApiResponse);
        }
        catch(Exception e){
            ApiResponse<List<ProductResDto>> apiResponse= new ApiResponse<>(Messages.Product.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);        }
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse<ProductResDto>> getProduct(@PathVariable @Min(1) int id){
        try{
            ProductResDto product= productService.getProductById(id);
            String message=Messages.Product.RECORD_FOUND;
            if(product == null) message=Messages.Product.RECORD_NOT_FOUND;
            ApiResponse<ProductResDto> apiResponse = new ApiResponse<>(String.format(message,id),product);
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<ProductResDto> apiResponse= new ApiResponse<>(Messages.Product.RECORD_NOT_FOUND_ERROR +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResDto>>> getProductByCategoryId(@PathVariable @Min(1) int categoryId){
        try{
            List<ProductResDto> productList=productService.getProductsByCategoryId(categoryId);
            ApiResponse<List<ProductResDto>> apiResponse = new ApiResponse<>(String.format(Messages.Product.RECORDS_FOUND_AND_LISTED_WITH_CATEGORY,categoryId),productList);
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<ProductResDto>> apiResponse= new ApiResponse<>(Messages.Product.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/product/name/{name}")
    public ResponseEntity<ApiResponse<List<ProductResDto>>> getProductByName(@PathVariable String name){
        try{
            List<ProductResDto> productList=productService.getProductByName(name);
            ApiResponse<List<ProductResDto>> apiResponse = new ApiResponse<>(String.format(Messages.Product.RECORDS_FOUND_AND_LISTED_WITH_NAME,name),productList);
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<List<ProductResDto>> apiResponse= new ApiResponse<>(Messages.Product.RECORD_NOT_FOUND_AND_LISTED_ERROR +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/product")
    public ResponseEntity<ApiResponse<String>> addProduct(@Valid @RequestBody ProductReqDto product){
        try{
            productService.createProduct(product);
            ApiResponse<String> apiResponse = new ApiResponse<>(Messages.Product.RECORD_CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Product.RECORD_CREATED_ERROR +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<ApiResponse<String>> updateProduct(@PathVariable @Min(1) int id,@Valid @RequestBody ProductReqDto product){
        try{
            productService.updateProduct(id, product);
            String message=Messages.Product.RECORD_UPDATED;
            if(product == null) message=Messages.Product.RECORD_NOT_FOUND;
            ApiResponse<String> apiResponse = new ApiResponse<>(String.format(message,id));
            return ResponseEntity.ok(apiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(Messages.Product.RECORD_UPDATED_ERROR +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable @Min(1) int id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>(String.format(Messages.Product.RECORD_DELETED_ERROR,id) +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
}
