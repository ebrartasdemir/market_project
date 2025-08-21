package com.market_p.market_p.mapper;

import com.market_p.market_p.dto.Product.ProductReqDto;
import com.market_p.market_p.dto.Product.ProductResDto;
import com.market_p.market_p.entity.Product;
import com.market_p.market_p.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    @Autowired
    private CategoryRepository categoryRepository;
    public ProductResDto productToProductResDto(Product product){
        ProductResDto productResDto = new ProductResDto();
        productResDto.setName(product.getName());
        productResDto.setDescription(product.getDescription());
        productResDto.setQuantity(product.getQuantity());
        productResDto.setPrice(product.getPrice());
        productResDto.setCategoryName(product.getCategory().getName());
        return productResDto;
    }
    public List<ProductResDto> productListToProductResDtoList(List<Product> products){
        List<ProductResDto> productResDtoList =new ArrayList<>();
        products.forEach(product -> productResDtoList.add(productToProductResDto(product)));
        return productResDtoList;
    }
    public Product productReqDtoToProduct(ProductReqDto productReqDto){
        Product product = new Product();
        product.setName(productReqDto.getName());
        product.setDescription(productReqDto.getDescription());
        product.setQuantity(productReqDto.getQuantity());
        product.setPrice(productReqDto.getPrice());
        return product;
    }
}
