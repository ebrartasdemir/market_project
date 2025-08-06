package com.market_p.market_p.service;

import com.market_p.market_p.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryId(int categoryId);
    Product getProductById(int id);
    List<Product> getProductByName(String name);
    void createProduct(Product product);
    void updateProduct(int id,Product product);
    void deleteProduct(int id);
}
