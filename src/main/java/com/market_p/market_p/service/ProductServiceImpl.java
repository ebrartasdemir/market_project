package com.market_p.market_p.service;

import com.market_p.market_p.dto.ProductReqDto;
import com.market_p.market_p.dto.ProductResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.entity.Product;
import com.market_p.market_p.mapper.ProductMapper;
import com.market_p.market_p.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<ProductResDto> getAllProducts() {
          List<Product> productList= productRepository.findAll();
          List<ProductResDto> productResDtoList =productMapper.productToProductResDtoList(productList);
          return productResDtoList;
    }

    @Override
    public List<ProductResDto> getProductsByCategoryId(int categoryId) {
        List<Product> productList=  productRepository.findByCategoryId(categoryId);
        List<ProductResDto> productResDtoList =productMapper.productToProductResDtoList(productList);
        return productResDtoList;

    }

    @Override
    public ProductResDto getProductById(int id) {
        Optional<Product> optionalProduct= productRepository.findById(id);
        Product product= optionalProduct.orElse(null);
        ProductResDto productResDto =productMapper.productToProductResDto(product);
        return productResDto;
        }

    @Override
    public List<ProductResDto> getProductByName(String name) {
        List<Product> productList=productRepository.findByName(name);
        List<ProductResDto> productResDtoList =productMapper.productToProductResDtoList(productList);
        return productResDtoList;
    }

    @Override
    public void createProduct(ProductReqDto newProduct) {
        Product product=productMapper.productReqDtoToProduct(newProduct);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(int id, ProductReqDto productReqDto) {
        Optional<Product> optProduct= productRepository.findById(id);
        Product newProduct=productMapper.productReqDtoToProduct(productReqDto);
        String newName=newProduct.getName();
        String newDescription=newProduct.getDescription();
        int newQuantity=newProduct.getQuantity();
        double newPrice=newProduct.getPrice();
        Category category=newProduct.getCategory();
        if(optProduct.isPresent()) {
            Product product = optProduct.get();
            if (newName != null) product.setName(newName);
            if (newDescription != null) product.setDescription(newDescription);
            if (newQuantity >= 0) product.setQuantity(newQuantity);
            if (newPrice >= 0) product.setPrice(newPrice);
            if(category != null) product.setCategory(category);
            productRepository.save(product);
        }
    }
    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

}
