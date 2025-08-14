package com.market_p.market_p.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market_p.market_p.controller.CategoryController;
import com.market_p.market_p.dto.ApiResponse;
import com.market_p.market_p.dto.CategoryResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.service.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    CategoryServiceImpl categoryService;

    private Category category;
    private CategoryResDto categoryResDto;

    @BeforeEach
    void setUp() {
        category = new Category("Elektronik","Teknolojik Ürünler");
        category.setId(1);
        categoryResDto = new CategoryResDto("Elektronik","Teknolojik Ürünler");
    }

    @Test
    void testGetAllCatefories_shouldListsAllCatefories () throws Exception {
        Category category2 = new Category("Kişisel Bakım","Sabun, deodorant gibi temizlik ürünleri");
        CategoryResDto categoryResDto2 = new CategoryResDto("Kişisel Bakım","Sabun, deodorant gibi temizlik ürünleri");
        List<CategoryResDto> categoryResDtoList= List.of(categoryResDto2,categoryResDto);
        when(categoryService.getAllCategories()).thenReturn(categoryResDtoList);
        mockMvc.perform(get("/api/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.Category.RECORDS_FOUND_AND_LISTED))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value(categoryResDto2.getName()))
                .andExpect(jsonPath("$.data[1].name").value(categoryResDto.getName()));
    }
    @Test
    void testGetAllCatefories_NotFound_shouldReturnEmptyList () throws Exception {
        List<CategoryResDto> categoryResDtoList= new ArrayList<>();
        ApiResponse<List<CategoryResDto>> apiResponse = new ApiResponse<>("All categories listed successfully.",categoryResDtoList);
        when(categoryService.getAllCategories()).thenReturn(categoryResDtoList);
        mockMvc.perform(get("/api/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.Category.RECORDS_FOUND_AND_LISTED))
                .andExpect(jsonPath("$.data.length()").value(0));
    }
    @Test
    void testGetAllCategories_serviceThrows_returns400() throws Exception {
        when(categoryService.getAllCategories()).thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(Messages.Category.RECORD_NOT_FOUND_AND_LISTED_ERROR)));
    }
    @Test
    void testGetCategoryById_shouldReturnCategory() throws Exception {
        when(categoryService.getCategoryById(1)).thenReturn(categoryResDto);
        mockMvc.perform(get("/api/category/{id}",1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Category.RECORD_FOUND,1)))
                .andExpect(jsonPath("$.data.name").value(categoryResDto.getName()));
    }
    @Test
    void testGetCategoryById_whenNotFound_shouldReturnNull() throws Exception {
        when(categoryService.getCategoryById(555)).thenReturn(null);
        mockMvc.perform(get("/api/category/{id}",555).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Category.RECORD_NOT_FOUND,555)))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }
    @Test
    void testGetCategoryById_invalidPathVariable() throws Exception {
        mockMvc.perform(get("/api/category/{id}", 0))
                .andExpect(status().isBadRequest());
        verify(categoryService,never()).getCategoryById(0);
    }
    @Test
    void testCreateCategory_shouldCreateCategory() throws Exception {
        doNothing().when(categoryService).createCategory(category);
        mockMvc.perform(post("/api/category").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Messages.Category.RECORD_CREATED));

    }
    @Test
    void testCreateCategory_serviceThrows_returns400() throws Exception {
        doThrow(new RuntimeException("")).when(categoryService).createCategory(any(Category.class));;
        mockMvc.perform(post("/api/category").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Messages.Category.RECORD_CREATED_ERROR))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }
    @Test
    void testCreateCategory_invalidBodyBlankName() throws Exception {
        Category category2 = new Category();
        mockMvc.perform(post("/api/category", 0))
                .andExpect(status().isBadRequest());
        verify(categoryService,never()).createCategory(category2);
    }
    @Test
    void testCreateCategory_invalidBodyMaxCharName() throws Exception {
        Category category2 = new Category("TemizleyicilerTemizleyicilerTemizleyicilerTemizleyiciler");
        mockMvc.perform(post("/api/category"))
                .andExpect(status().isBadRequest());
        verify(categoryService,never()).createCategory(category2);
    }
    @Test
    void testUpdateCategory_shouldUpdateCategory() throws Exception {
        Category category2=new Category("Hazır Gıda","Konserve, Hazır, Paketlenmiş Yemek ürünleri.");
        doNothing().when(categoryService).updateCategory(1,category2);
        mockMvc.perform(put("/api/category/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(String.format(Messages.Category.RECORD_UPDATED,1)));
    }
    @Test
    void testUpdateCategory_whenNotFound_shouldThrow400() throws Exception {
        Category bodyCategory=new Category("Hazır Gıda","Konserve, Hazır, Paketlenmiş Yemek ürünleri.");
        doThrow(new RuntimeException(String.format(Messages.Category.RECORD_NOT_FOUND,231324))).when(categoryService).updateCategory(eq(231324),any(Category.class));
        mockMvc.perform(put("/api/category/{id}", 231324)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bodyCategory)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateCategory_whenBodyEmpty_shouldThrow400() throws Exception {
        Category category2 = new Category();
        doThrow(new RuntimeException(Messages.EMPTY_BODY)).when(categoryService).updateCategory(anyInt(),refEq(category2));
        mockMvc.perform(put("/api/category/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category2)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testUpdateCategory_invalidId() throws Exception {
        mockMvc.perform(put("/api/category/{id}",0))
                .andExpect(status().isBadRequest());
        verify(categoryService,never()).createCategory(category);
}
    @Test
    void testDeleteCategory_shouldDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(1);
        mockMvc.perform(delete("/api/category/{id}",1))
                .andExpect(status().isNoContent());
        verify(categoryService).deleteCategory(1);
    }
    @Test
    void testDeleteCategory_invalidId() throws Exception {
        mockMvc.perform(delete("/api/product/{id}",0))
                .andExpect(status().isBadRequest());
        verify(categoryService,never()).deleteCategory(1);
    }
    @Test
    void testDeleteCategory_whenCategoryNotFound() throws Exception {
        doThrow(new RuntimeException(String.format(Messages.Category.RECORD_NOT_FOUND,2))).when(categoryService).deleteCategory(2);
        mockMvc.perform(delete("/api/category/{id}",2))
                .andExpect(status().isBadRequest());
    }
}
