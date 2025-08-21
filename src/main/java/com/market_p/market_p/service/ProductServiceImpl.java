package com.market_p.market_p.service;

import com.market_p.market_p.dto.Product.ProductReqDto;
import com.market_p.market_p.dto.Product.ProductResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.entity.Product;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.mapper.ProductMapper;
import com.market_p.market_p.repository.CategoryRepository;
import com.market_p.market_p.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.market_p.market_p.utils.Jsonify.toJson;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    private String message;
    private static final Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductResDto> getAllProducts() {
        logger.info("[ProductService] Service Method: getAllProducts - ( ) -  ------------");
        logger.info("[ProductService] Input: null");
        List<Product> productList= productRepository.findAll();
        List<ProductResDto> productResDtoList =productMapper.productListToProductResDtoList(productList);
        logger.info("[ProductService] Output: DTO List => {}",productResDtoList);
        logger.info("[ProductService] Listed Successfully");//buradaki mesaj da mı constant olacak?
        return productResDtoList;
    }

    @Override
    public List<ProductResDto> getProductsByCategoryId(int categoryId) {
        logger.info("[ProductService] Service Method: getProductsByCategoryId - ( categoryId ) -  ------------");
        logger.info("[ProductService] Input: categoryId => {}",categoryId);
        List<Product> productList=  productRepository.findByCategoryId(categoryId);
        List<ProductResDto> productResDtoList =productMapper.productListToProductResDtoList(productList);
        logger.info("[ProductService] Output: DTO List => {}",productResDtoList);
        logger.info("[ProductService] Found Successfully");
        return productResDtoList;
    }

    @Override
    public ProductResDto getProductById(int id) {
        logger.info("[ProductService] Service Method: getProductById - ( id ) -  ------------");
        logger.info("[ProductService] Input: id => {}",id);
        Optional<Product> optionalProduct= productRepository.findById(id);
        Product product= optionalProduct.orElse(null);
        ProductResDto productResDto =productMapper.productToProductResDto(product);
        logger.info("[ProductService] Output: DTO => {}",productResDto);
        logger.info("[ProductService] Found Successfully");
        return productResDto;
        }

    @Override
    public List<ProductResDto> getProductByName(String name) {
        logger.info("[ProductService] Service Method: getProductByName - ( name ) -  ------------");
        logger.info("[ProductService] Input: name => {}",name);
        List<Product> productList=productRepository.findByName(name);
        List<ProductResDto> productResDtoList =productMapper.productListToProductResDtoList(productList);
        logger.info("[ProductService] Output: DTO List => {}",productResDtoList);
        logger.info("[ProductService] Listed Successfully");
        return productResDtoList;
    }

    @Override
    public void createProduct(ProductReqDto newProduct) {
        logger.info("[ProductService] Service Method: getProductByName - ( name ) -  ------------");
        logger.info("[ProductService] Input: DTO => {}",toJson(newProduct));
        if(newProduct==null||newProduct.getName()==null || newProduct.getName().equals(""))return;
        if(newProduct.getCategoryId()==0) return;
        Product product=productMapper.productReqDtoToProduct(newProduct);
        Category category=categoryRepository.findById(newProduct.getCategoryId()).orElse(null);
        if(category==null){throw new RuntimeException(String.format(Messages.Category.RECORD_NOT_FOUND,newProduct.getCategoryId()));}
        product.setCategory(category);
        if(product.getCategory()!=null){
            productRepository.save(product);
            logger.info("[ProductService] Output: null");
            logger.info("[ProductService] Created Successfully");
        }
        else logger.warn("[ProductService] Create Failed");
    }

    @Override
    public void updateProduct(int id, ProductReqDto productReqDto) {
        logger.info("[ProductService] Service Method: updateProduct - ( id ) -  ------------");
        logger.info("[ProductService] Input: id => {}",id);
        Optional<Product> optProduct= productRepository.findById(id);
        if(optProduct.isEmpty()) throw new RuntimeException(String.format(Messages.Product.RECORD_NOT_FOUND,id));
        if(productReqDto.getName()==null &&
          productReqDto.getDescription()==null &&
          productReqDto.getCategoryId()==0 &&
          productReqDto.getPrice()==-1&&
          productReqDto.getQuantity()==-1)
        {message=Messages.EMPTY_BODY;
            logger.error("[ProductService] Error: {}",message);
            throw new RuntimeException(message);}
        if(optProduct.isPresent()) {
            Product newProduct=productMapper.productReqDtoToProduct(productReqDto);
            Category newCategory=categoryRepository.findById(newProduct.getCategory().getId()).orElse(null);
            if(newCategory==null){throw new RuntimeException(String.format(Messages.Category.RECORD_NOT_FOUND,newProduct.getCategory().getId()));}
            newProduct.setCategory(newCategory);
            String newName=newProduct.getName();
            String newDescription=newProduct.getDescription();
            int newQuantity=newProduct.getQuantity();
            double newPrice=newProduct.getPrice();
            Category category=newProduct.getCategory();
            Product product = optProduct.get();
            if (newName != null&& newName!="") product.setName(newName);
            if (newDescription != null) product.setDescription(newDescription);
            if (newQuantity >= 0) product.setQuantity(newQuantity);
            if (newPrice >= 0) product.setPrice(newPrice);
            if(category != null) product.setCategory(category);
            logger.info("[ProductService] Output: null");
            logger.info("[ProductService] Updated Successfully");
            productRepository.save(product);
        }
        else {message=String.format(Messages.Product.RECORD_NOT_FOUND,id);
            logger.error("[ProductService] Error: {}",message);
            logger.warn("[ProductService] Update Failed");
            throw new RuntimeException(message);};
    }
    @Override
    public void deleteProduct(int id) {
        logger.info("[ProductService] Service Method: deleteProduct - ( id ) -  ------------");
        logger.info("[ProductService] Input: id => {}",id);
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            logger.info("[ProductService] Output: null");
            logger.info("[ProductService] Deleted Successfully");
        }
        else {message=String.format(Messages.Product.RECORD_NOT_FOUND,id);
            logger.error("[ProductService] Error: {}",message);
            logger.warn("[ProductService] Delete Failed");
            throw new RuntimeException(message);
        }

    }

}
