package com.market_p.market_p.service;

import com.market_p.market_p.entity.Product;
import com.market_p.market_p.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return  (List<Product>) productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) {
        return  productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> optionalProduct= productRepository.findById(id);
        return optionalProduct.orElse(null);
        }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(int id,Product newProduct) {
        Optional<Product> optProduct= productRepository.findById(id);
        String newName=newProduct.getName();
        String newDescription=newProduct.getDescription();
        int newQuantity=newProduct.getQuantity();
        double newPrice=newProduct.getPrice();
        if(optProduct.isPresent()) {
            Product product = optProduct.get();
            if (newName != null) product.setName(newName);
            if (newDescription != null) product.setDescription(newDescription);
            if (newQuantity >= 0) product.setQuantity(newQuantity);
            if (newPrice >= 0) product.setPrice(newPrice);
            productRepository.save(product);
        }
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

}
