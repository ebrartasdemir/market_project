package com.market_p.market_p.service;

import com.market_p.market_p.dto.ProductReqDto;
import com.market_p.market_p.dto.ProductResDto;
import com.market_p.market_p.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductResDto> getAllProducts();
    List<ProductResDto> getProductsByCategoryId(int categoryId);
    ProductResDto getProductById(int id);
    List<ProductResDto> getProductByName(String name);
    void createProduct(ProductReqDto product);
    void updateProduct(int id, ProductReqDto product);
    void deleteProduct(int id);
}
