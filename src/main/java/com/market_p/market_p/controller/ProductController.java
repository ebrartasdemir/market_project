package com.market_p.market_p.controller;

import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.ProductReqDto;
import com.market_p.market_p.dto.ProductResDto;
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
            ApiResponse<List<ProductResDto>> productApiResponse = new ApiResponse<>("All products listed successfully.",productList);
            return ResponseEntity.ok(productApiResponse);
        }
        catch(Exception e){
            ApiResponse<List<ProductResDto>> apiResponse= new ApiResponse<>("Products cannot be listed, something went wrong.\n"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);        }
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse<ProductResDto>> getProduct(@PathVariable @Min(1) int id){
        try{
            ProductResDto product= productService.getProductById(id);
            ApiResponse<ProductResDto> productApiResponse = new ApiResponse<>("Product with id: "+id+" found successfully.",product);
            return ResponseEntity.ok(productApiResponse);
        }
        catch(Exception e){
            ApiResponse<ProductResDto> apiResponse= new ApiResponse<>("Product cannot be found, something went wrong.\n"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResDto>>> getProductByCategoryId(@PathVariable @Min(1) int categoryId){
        try{
            List<ProductResDto> productList=productService.getProductsByCategoryId(categoryId);
            ApiResponse<List<ProductResDto>> productApiResponse = new ApiResponse<>("All products listed successfully.",productList);
            return ResponseEntity.ok(productApiResponse);
        }
        catch(Exception e){
            ApiResponse<List<ProductResDto>> apiResponse= new ApiResponse<>("Products cannot be listed, something went wrong.\n"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/product/name/{name}")//%like%
    public ResponseEntity<ApiResponse<List<ProductResDto>>> getProductByName(@PathVariable String name){
        try{
            List<ProductResDto> productList=productService.getProductByName(name);
            ApiResponse<List<ProductResDto>> productApiResponse = new ApiResponse<>("All products listed successfully.",productList);
            return ResponseEntity.ok(productApiResponse);
        }
        catch(Exception e){
            ApiResponse<List<ProductResDto>> apiResponse= new ApiResponse<>("Products cannot be listed, something went wrong.\n"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PostMapping("/product")
    public ResponseEntity<ApiResponse<String>> addProduct(@Valid @RequestBody ProductReqDto product){
        try{
            productService.createProduct(product);
            ApiResponse<String> productApiResponse = new ApiResponse<>("Product created successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(productApiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>("Products cannot be created, something went wrong.\n"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<ApiResponse<String>> updateProduct(@PathVariable @Min(1) int id,@Valid @RequestBody ProductReqDto product){
        try{
            productService.updateProduct(id, product);
            ApiResponse<String> productApiResponse = new ApiResponse<>("Product updated successfully.");
            return ResponseEntity.ok(productApiResponse);
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>("Products cannot be updated, something went wrong.\n"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable @Min(1) int id){
        try{
            productService.deleteProduct(id);
            ApiResponse<String> productApiResponse = new ApiResponse<>("Product deleted successfully.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch(Exception e){
            ApiResponse<String> apiResponse= new ApiResponse<>("Products cannot be deleted, something went wrong.\n"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
}
