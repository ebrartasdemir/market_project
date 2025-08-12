package com.market_p.market_p.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.market_p.market_p.dto.CategoryResDto;
import com.market_p.market_p.entity.Category;
import com.market_p.market_p.mapper.CategoryMapper;
import com.market_p.market_p.repository.CategoryRepository;
import org.assertj.core.api.AssertionsForClassTypes;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Captor
    private ArgumentCaptor<Category> categoryCaptor;


    Category category;
    CategoryResDto categoryResDto;


    @BeforeEach
    public void setup() {
        category=new Category("Elektronik","Teknolojik Ürünler");
        category.setId(1);
        categoryResDto=new CategoryResDto("Elektronik","Teknolojik Ürünler");
    }
    @Test
    void testGetAllCategories_ShouldReturn_AllCategories() {
        Category category2=new Category("Hazır Gıda","Konserve, Hazır, Paketlenmiş Yemek ürünleri.");
        category2.setId(2);
        CategoryResDto categoryResDto2=new CategoryResDto("Hazır Gıda","Konserve, Hazır, Paketlenmiş Yemek ürünleri.");
        List<Category> categoryList=List.of(category,category2);
        List<CategoryResDto> categoryResDtoList=List.of(categoryResDto,categoryResDto2);

        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryMapper.categoryListToCategoryResDtoList(categoryList)).thenReturn(categoryResDtoList);

        List<CategoryResDto> result = categoryService.getAllCategories();
        assertThat(result).isEqualTo(categoryResDtoList);
    }
    @Test
    void testGetAllCategories_whenEmpty_shouldReturnEmptyList() {
        List<Category> emptyList = List.of();
        List<CategoryResDto> emptyDtoList = List.of();

        when(categoryRepository.findAll()).thenReturn(emptyList);
        when(categoryMapper.categoryListToCategoryResDtoList(emptyList)).thenReturn(emptyDtoList);

        List<CategoryResDto> result = categoryService.getAllCategories();

        assertThat(result).isEqualTo(emptyDtoList);
    }
    @Test
     void testGetGategoryById_shouldReturnCategory() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(categoryMapper.categoryToCategoryResDto(category)).thenReturn(categoryResDto);
        CategoryResDto result=categoryService.getCategoryById(1);
        assertThat(result).isEqualTo(categoryResDto);
    }
    @Test
    void testGetGategoryById_whenEmpty_shouldReturnNull(){
        when(categoryRepository.findById(2)).thenReturn(Optional.empty());
        when(categoryMapper.categoryToCategoryResDto(isNull())).thenReturn(null);
        CategoryResDto result = categoryService.getCategoryById(2);
        assertThat(result).isNull();
    }
    @Test
    void testCreateCategory_whenFielsAreNull_shouldReturnNull( ) {
        Category category1=new Category();
        categoryService.createCategory(category1);
        verify(categoryRepository,never()).save(any());
    }
    @Test
    void testUpdateCategory_shouldUpdateCategory( ) {
        Category bodyCategory=new Category("İçecek","Soğuk Sıcak İçecekler");
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        categoryService.updateCategory(1,bodyCategory);
        assertThat(category.getName()).isEqualTo(bodyCategory.getName());
        assertThat(category.getDescription()).isEqualTo(bodyCategory.getDescription());
        verify(categoryRepository).save(category);
    }
    @Test
    void testUpdateCategory_whenBodyIsEmpty_shouldReturnNull ( ) {
        Category bodyCategory=new Category();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        categoryService.updateCategory(1,bodyCategory);
        verify(categoryRepository,never()).save(any());
    }
    @Test
    void testUpdateCategory_whenCategoryNotFound_shouldReturnNull ( ) {
        Category bodyCategory=new Category("İçecek","Soğuk Sıcak İçecekler");
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());
        categoryService.updateCategory(1,bodyCategory);
        verify(categoryRepository,never()).save(any());
    }
    @Test
    void testDeleteCategory_shouldDeleteCategory( ) {
        when(categoryRepository.existsById(1)).thenReturn(true);
        categoryService.deleteCategory(1);
        verify(categoryRepository,times(1)).deleteById(1);
    }
    @Test
    void testDeleteCategory_whenEmpty_shouldReturnNull( ) {
        when(categoryRepository.existsById(2)).thenReturn(false);
        categoryService.deleteCategory(2);
        verify(categoryRepository,never()).deleteById(anyInt());
    }
}
