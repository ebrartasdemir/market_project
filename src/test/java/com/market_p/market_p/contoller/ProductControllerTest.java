package com.market_p.market_p.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market_p.market_p.controller.ProductController;

import com.market_p.market_p.dto.ProductReqDto;
import com.market_p.market_p.dto.ProductResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.entity.Product;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    ProductService productService;

    private Product product;
    private Category category;
    private ProductResDto productResDto;

    @BeforeEach
    void setUp() {
        category = new Category("Elektronik", "Teknolojik Ürünler");
        category.setId(1);
        product = new Product(category, 5, 5.5, "Intel...", "Hp Laptop");
        product.setId(1);
        productResDto = new ProductResDto("Elektronik", 5, 5.5, "Intel...", "Hp Laptop");
    }

    @Test
    void testGetAllProducts_shouldListsAllProducts () throws Exception {
        ProductResDto productResDto2=new ProductResDto("Elektronik",6,6.6,"Son model teknoloji","Xiamoi Kullaklık");
        List<ProductResDto>productResDtoList=List.of(productResDto,productResDto2);
        when(productService.getAllProducts()).thenReturn(productResDtoList);
        mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.Product.RECORDS_FOUND_AND_LISTED))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value(productResDto.getName()))
                .andExpect(jsonPath("$.data[1].name").value(productResDto2.getName()));
    }
    @Test
    void testGetAllProducts_NotFound_shouldReturnEmptyList () throws Exception {
        List<ProductResDto> productResDtoList= new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(productResDtoList);
        mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.Product.RECORDS_FOUND_AND_LISTED))
                .andExpect(jsonPath("$.data.length()").value(0));
    }
    @Test
    void testGetAllProducts_serviceThrows_returns400() throws Exception {
        when(productService.getAllProducts()).thenThrow(new RuntimeException(""));
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Messages.Product.RECORD_NOT_FOUND_AND_LISTED_ERROR));
    }
    @Test//farklı sonuç
    void testGetAllProductsByCategoryId_shouldListsAllProducts () throws Exception {
        ProductResDto productResDto2=new ProductResDto("Elektronik",6,6.6,"Son model teknoloji","Xiamoi Kullaklık");
        List<ProductResDto>productResDtoList=List.of(productResDto,productResDto2);
        when(productService.getProductsByCategoryId(1)).thenReturn(productResDtoList);
        mockMvc.perform(get("/api/products/category/{id}",1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Product.RECORDS_FOUND_AND_LISTED_WITH_CATEGORY,1)))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].categoryName").value(productResDto.getCategoryName()))
                .andExpect(jsonPath("$.data[1].categoryName").value(productResDto2.getCategoryName()));
    }
    @Test
    void testGetAllProductsByCategoryId_NotFound_shouldReturnEmptyList () throws Exception {
        List<ProductResDto> productResDtoList= new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(productResDtoList);
        mockMvc.perform(get("/api/products/category/{id}",4).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Product.RECORDS_FOUND_AND_LISTED_WITH_CATEGORY,4)))
                .andExpect(jsonPath("$.data.length()").value(0));
    }
    @Test//farklı sonuç
    void testGetAllProductsByName_shouldListsAllProducts () throws Exception {
        List<ProductResDto>productResDtoList=List.of(productResDto);
        when(productService.getProductByName("Hp Laptop")).thenReturn(productResDtoList);
        mockMvc.perform(get("/api/product/name/{name}","Hp Laptop").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Product.RECORDS_FOUND_AND_LISTED_WITH_NAME,"Hp Laptop")))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].name").value(productResDto.getName()));
    }
    @Test
    void testGetAllProductsByName_NotFound_shouldReturnEmptyList () throws Exception {
        List<ProductResDto> productResDtoList= new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(productResDtoList);
        mockMvc.perform(get("/api/product/name/sadfasdf").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Product.RECORDS_FOUND_AND_LISTED_WITH_NAME,"sadfasdf")))
                .andExpect(jsonPath("$.data.length()").value(0));
    }
    @Test
    void testGetProductById_shouldReturnProduct() throws Exception {
        when(productService.getProductById(1)).thenReturn(productResDto);
        mockMvc.perform(get("/api/product/{id}",1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Product.RECORD_FOUND,1)))
                .andExpect(jsonPath("$.data.name").value(productResDto.getName()));
    }
    @Test
    void testGetProductById_whenNotFound_shouldReturnNull() throws Exception {
        when(productService.getProductById(3)).thenReturn(null);
        mockMvc.perform(get("/api/product/{id}",3).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Product.RECORD_NOT_FOUND,3)))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }

//    //    @Test
////    void testGetCategoryById_serviceThrows_returns400() throws Exception {
////        String id="dfsgsd";
////        when(categoryService.getCategoryById(id)).thenThrow(new RuntimeException());
////        mockMvc.perform(get("/api/category/{id}",567).accept(MediaType.APPLICATION_JSON))
////                .andExpect(status().isBadRequest())
////                .andExpect(jsonPath("$.message", containsString("Category cannot be found. Something went wrong.")));
////    }
    @Test
    void testGetProductById_invalidPathVariable() throws Exception {
        mockMvc.perform(get("/api/product/{id}", 0))
                .andExpect(status().isBadRequest());
        verify(productService,never()).getProductById(0);
    }
    @Test
    void testCreatePrdouct_shouldCreateCategory() throws Exception {
    ProductReqDto productReqDto=new ProductReqDto(1, 5, 5.5, "Intel...", "Hp Laptop");
        doNothing().when(productService).createProduct(productReqDto);
        mockMvc.perform(post("/api/product").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Messages.Product.RECORD_CREATED));
    }

    @Test
    void testCreateProduct_serviceThrows_returns400() throws Exception {
        doThrow(new RuntimeException("")).when(productService).createProduct(any(ProductReqDto.class));
        mockMvc.perform(post("/api/product").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Messages.Product.RECORD_CREATED_ERROR))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }
    @Test
    void testCreateProduct_invalidBodyBlankName() throws Exception {
        ProductReqDto dto=new ProductReqDto();
        mockMvc.perform(post("/api/product", 0))
                .andExpect(status().isBadRequest());
        verify(productService,never()).createProduct(dto);
    }
    @Test
    void testCreateProduct_invalidBodyMaxCharName() throws Exception {
        ProductReqDto product1 = new ProductReqDto(1,100,1923.0,"","LAptopLAptopLAptopLAptopLAptopLAptopLAptop");
        mockMvc.perform(post("/api/product"))
                .andExpect(status().isBadRequest());
        verify(productService,never()).createProduct(product1);
    }
    @Test
    void testUpdateProduct_shouldUpdateCategory() throws Exception {
        ProductReqDto productReqDto=new ProductReqDto(1, 5, 5.5, "Intel...", "Hp Laptop");
        doNothing().when(productService).updateProduct(1,productReqDto);
        mockMvc.perform(put("/api/product/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Product.RECORD_UPDATED,1)));
    }
        @Test
    void testUpdateProduct_whenNotFound_shouldThrow400() throws Exception {
            ProductReqDto productReqDto=new ProductReqDto(1, 5, 5.5, "Intel...", "Hp Laptop");
            doThrow(new RuntimeException(String.format(Messages.Product.RECORD_NOT_FOUND,231324))).when(productService).updateProduct(anyInt(), any(ProductReqDto.class));
            mockMvc.perform(put("/api/product/{id}", 231324)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productReqDto)))
                    .andExpect(jsonPath("$.message").value(Messages.Product.RECORD_UPDATED_ERROR+String.format(Messages.Product.RECORD_NOT_FOUND,231324)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateProduct_invalidId() throws Exception {
        ProductReqDto productReqDto=new ProductReqDto(1, 5, 5.5, "Intel...", "Hp Laptop");
        mockMvc.perform(put("/api/product/{id}",0))
                .andExpect(status().isBadRequest());
        verify(productService,never()).updateProduct(0,productReqDto);
    }
    @Test
    void testDeleteProduct_shouldDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1);
        mockMvc.perform(delete("/api/product/{id}",1))
                .andExpect(status().isNoContent());
        verify(productService).deleteProduct(1);
    }
    @Test
    void testDeleteProduct_invalidId() throws Exception {
        mockMvc.perform(delete("/api/product/{id}",0))
                .andExpect(status().isBadRequest());
        verify(productService,never()).deleteProduct(0);
    }
    @Test
   void testDeleteProduct_whenProductNotFound() throws Exception {
        doThrow(new RuntimeException("")).when(productService).deleteProduct(5555);
        mockMvc.perform(delete("/api/product/{id}",5555))
               .andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value(String.format(Messages.Product.RECORD_DELETED_ERROR,5555)))
                .andExpect(jsonPath("$.data").value(nullValue()));
   }
}
