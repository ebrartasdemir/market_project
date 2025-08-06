package com.market_p.market_p.controller;

import com.market_p.market_p.entity.Product;
import com.market_p.market_p.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProduct(){
        return  productService.getAllProducts();
    }
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable int id){
        return  productService.getProductById(id);
    }
    @GetMapping("/products/category/{categoryId}")
    public List<Product> getProductByCategoryId(@PathVariable int categoryId){
        return  productService.getProductsByCategoryId(categoryId);
    }
    @GetMapping("/product/name/{name}")
    public List<Product> getProductByName(@PathVariable String name){
        return  productService.getProductByName(name);
    }
    @PostMapping("/product")
    public void addProduct(@RequestBody Product product){
        productService.createProduct(product);
    }
    @PutMapping("/product/{id}")
    public void updateProduct(@PathVariable int id,@RequestBody Product product){
        productService.updateProduct(id, product);
    }
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
    }
}
