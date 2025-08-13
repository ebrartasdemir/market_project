package com.market_p.market_p.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.market_p.market_p.dto.ProductReqDto;
import com.market_p.market_p.dto.ProductResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.entity.Product;
import com.market_p.market_p.mapper.ProductMapper;
import com.market_p.market_p.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    @Captor
    private ArgumentCaptor<Product> productCaptor;


    Category category;
    Product product;
    ProductResDto productResDto;


    @BeforeEach
    public void setup() {
        category=new Category("Elektronik","Teknolojik Ürünler");
        category.setId(1);
        product=new Product(category,5,5.5,"Intel...","Hp Laptop");
        product.setId(1);
        productResDto=new ProductResDto("Elektronik",5,5.5,"Intel...","Hp Laptop");
    }
    @Test
    void testGetAllCategories_ShouldReturn_AllCategories() {
        Product product2=new Product(category,6,6.6,"Son model teknoloji","Xiamoi Kullaklık");
        product2.setId(2);
        ProductResDto productResDto2=new ProductResDto("Elektronik",6,6.6,"Son model teknoloji","Xiamoi Kullaklık");
        List<Product> productList=List.of(product,product2);
        List<ProductResDto>productResDtoList=List.of(productResDto,productResDto2);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.productListToProductResDtoList(productList)).thenReturn(productResDtoList);

        List<ProductResDto> result = productService.getAllProducts();
        assertThat(result).isEqualTo(productResDtoList);
    }
    @Test
    void testGetAllCategories_whenEmpty_shouldReturnEmptyList() {
        List<Product> emptyList = List.of();
        List<ProductResDto> emptyDtoList = List.of();

        when(productRepository.findAll()).thenReturn(emptyList);
        when(productMapper.productListToProductResDtoList(emptyList)).thenReturn(emptyDtoList);

        List<ProductResDto> result = productService.getAllProducts();

        assertThat(result).isEqualTo(emptyDtoList);
    }
    @Test
     void testGetProductById_shouldReturnProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productMapper.productToProductResDto(product)).thenReturn(productResDto);
        ProductResDto result=productService.getProductById(1);
        assertThat(result).isEqualTo(productResDto);
    }
    @Test
    void testGetProductByName_shouldReturnProduct(){
        String productName="Hp Laptop";
        List<Product> productList=List.of(product);
        List<ProductResDto> productResDtoList=List.of(productResDto);
        when(productRepository.findByName(productName)).thenReturn(productList);
        when(productMapper.productListToProductResDtoList(productList)).thenReturn(productResDtoList);
        List<ProductResDto> result=productService.getProductByName(productName);
        assertThat(result).isEqualTo(productResDtoList);
    }
    @Test
    void testGetProductByName_whenNotFound_shouldReturnEmptyList(){
        String productName="Msi Laptop";
        List<Product> productList=new ArrayList<>();
        List<ProductResDto> productResDtoList=new ArrayList<>();
        when(productRepository.findByName(productName)).thenReturn(productList);
        when(productMapper.productListToProductResDtoList(productList)).thenReturn(productResDtoList);
        List<ProductResDto> result=productService.getProductByName(productName);
        assertThat(result).isEqualTo(List.of());
    }
    @Test
    void testGetProductById_whenNotFound_shouldReturnNull() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());
        when(productMapper.productToProductResDto(null)).thenReturn(null);
        ProductResDto result=productService.getProductById(99);
        assertThat(result).isEqualTo(null);
    }
    @Test
    void testGetProductsByCategoryId_shouldReturnProducts( ) {
        Category category2=new Category("Kişisel Bakım","Temizleyci, canlandırıcılar");
        category2.setId(2);
        Product product2=new Product(category,6,6.6,"Son model teknoloji","Xiamoi Kullaklık");
        ProductResDto productResDto2=new ProductResDto("Elektronik",6,6.6,"Son model teknoloji","Xiamoi Kullaklık");
        product2.setId(2);
        Product product3=new Product(category2,9,0.99,"Nazik Temizlik","Celenes Yüz Yıkama Jeli");
        product3.setId(3);
        List<Product> productList=List.of(product,product2);
        List<ProductResDto>productResDtoList=List.of(productResDto,productResDto2);
        when(productRepository.findByCategoryId(category.getId())).thenReturn(productList);
        when(productMapper.productListToProductResDtoList(productList)).thenReturn(productResDtoList);
        List<ProductResDto> result=productService.getProductsByCategoryId(category.getId());
        assertThat(result).isEqualTo(productResDtoList);
    }
    @Test
    void testGetProductsByCategoryId_whenCategoryNotFound_shouldReturnEmptyList( ) {
        Category category2=new Category("Kişisel Bakım","Temizleyci, canlandırıcılar");
        category2.setId(2);
        List<Product> productList=new ArrayList<>();
        List<ProductResDto>productResDtoList=new ArrayList<>();
        when(productRepository.findByCategoryId(category2.getId())).thenReturn(productList);
        when(productMapper.productListToProductResDtoList(productList)).thenReturn(productResDtoList);
        List<ProductResDto> result=productService.getProductsByCategoryId(category2.getId());
        assertThat(result).isEqualTo(productResDtoList);
    }

    @Test
    void testCreateProduct_shouldCreateProduct( ) {
        ProductReqDto productReqDto=new ProductReqDto(1,5,5.5,"Intel...","Hp Laptop");
        when(productMapper.productReqDtoToProduct(productReqDto)).thenReturn(product);
        productService.createProduct(productReqDto);
        verify(productRepository,times(1)).save(productCaptor.capture());
        Product newProduct=productCaptor.getValue();
        assertThat(newProduct.getId()).isEqualTo(product.getId());
    }
    @Test
    void testCreateProduct_whenFieldsAreEmpty( ) {
        ProductReqDto productReqDto=new ProductReqDto();
        productService.createProduct(productReqDto);
        verify(productRepository,never()).save(any());
    }
    @Test
    void testCreateProduct_whenCategoryIsEmpty( ) {
        ProductReqDto productReqDto=new ProductReqDto(2,5,5.5,"Intel...","Msi Laptop");
        Product product2=new Product();
        product2.setId(2);
        product2.setName("Msi Laptop");
        product2.setDescription("Intel...");
        product2.setQuantity(5);
        product2.setPrice(5.5);
        when(productMapper.productReqDtoToProduct(productReqDto)).thenReturn(product2);
        productService.createProduct(productReqDto);
        verify(productRepository,never()).save(any());
    }

    @Test
    void testUpdateProduct_shouldUpdateProduct( ) {
        Product bodyProduct=new Product(category,6,6.6,"Son model teknoloji","Xiamoi Kullaklık");
        bodyProduct.setId(2);
        ProductReqDto bodyProductReqDto=new ProductReqDto(1,6,6.6,"Son model teknoloji","Xiamoi Kullaklık");
        when(productMapper.productReqDtoToProduct(bodyProductReqDto)).thenReturn(bodyProduct);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        productService.updateProduct(1,bodyProductReqDto);
        assertThat(product.getName()).isEqualTo(bodyProduct.getName());
        assertThat(product.getDescription()).isEqualTo(bodyProduct.getDescription());
        assertThat(product.getPrice()).isEqualTo(bodyProduct.getPrice());
        assertThat(product.getQuantity()).isEqualTo(bodyProduct.getQuantity());
        assertThat(product.getCategory().getId()).isEqualTo(bodyProduct.getCategory().getId());
        verify(productRepository).save(product);
    }
    @Test
    void testUpdateProduct_whenProductNotFound_shouldProductBeEmpty( ) {
        ProductReqDto productReqDto=new ProductReqDto(1,5,5.5,"Intel...","Hp Laptop");
        when(productRepository.findById(2)).thenReturn(Optional.empty());
        productService.updateProduct(2,productReqDto);
        verify(productRepository,never()).save(any());
        verify(productMapper,never()).productListToProductResDtoList(any());
    }

    @Test
    void testUpdateProduct_whenBodyNotFull( ) {
        Product bodyProduct=new Product();
        bodyProduct.setQuantity(10);
        bodyProduct.setPrice(1000);
        bodyProduct.setDescription("Son model teknoloji");
        ProductReqDto bodyProductReqDto=new ProductReqDto();
        bodyProductReqDto.setQuantity(10);
        bodyProductReqDto.setPrice(1000);
        bodyProductReqDto.setDescription("Son model teknoloji");
        when(productMapper.productReqDtoToProduct(bodyProductReqDto)).thenReturn(bodyProduct);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        productService.updateProduct(1,bodyProductReqDto);
        assertThat(product.getDescription()).isEqualTo(bodyProduct.getDescription());
        assertThat(product.getPrice()).isEqualTo(bodyProduct.getPrice());
        assertThat(product.getQuantity()).isEqualTo(bodyProduct.getQuantity());
        verify(productRepository).save(product);
    }
    @Test
    void testUpdateProduct_whenBodyOnlyHasOneField( ) {
        Category category2=new Category("Product","Temizleyci, canlandırıcılar");
        category2.setId(2);
        Product bodyProduct=new Product();
        bodyProduct.setCategory(category2);
        ProductReqDto bodyProductReqDto=new ProductReqDto();
        bodyProductReqDto.setCategoryId(category2.getId());
        when(productMapper.productReqDtoToProduct(bodyProductReqDto)).thenReturn(bodyProduct);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        productService.updateProduct(1,bodyProductReqDto);
        assertThat(product.getCategory().getId()).isEqualTo(bodyProduct.getCategory().getId());
        verify(productRepository).save(product);
    }
    @Test
    void testDeleteProduct_shouldDeleteProduct( ) {
        when(productRepository.existsById(1)).thenReturn(true);
        productService.deleteProduct(1);
        verify(productRepository,times(1)).deleteById(1);
    }
    @Test
    void testDeleteProduct_whenProductNotFound( ) {
        when(productRepository.existsById(3)).thenReturn(false);
        assertThatThrownBy(() -> productService.deleteProduct(3))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Product not found");
        verify(productRepository,never()).deleteById(3);
    }
}
