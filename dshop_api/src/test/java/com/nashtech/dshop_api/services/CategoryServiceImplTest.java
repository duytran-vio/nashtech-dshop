package com.nashtech.dshop_api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nashtech.dshop_api.data.entities.Category;
import com.nashtech.dshop_api.data.repositories.CategoryRepository;
import com.nashtech.dshop_api.dto.requests.CategoryCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.CategoryDto;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.CategoryMapper;
import com.nashtech.dshop_api.services.impl.CategoryServiceImpl;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetAllCategories_thenReturnCategoryDtoList() {
        // Arrange
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(new CategoryDto());

        // Act
        List<CategoryDto> result = categoryService.getAllCategories();

        // Assert
        assertEquals(2, result.size());

        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(2)).toDto(any(Category.class));
    }

    @Test
    public void whenGetCategoryById_thenReturnCategoryDto() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        CategoryDto result = categoryService.getCategoryById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(categoryId, result.getId());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    public void givenNotExistCategoryId_whenGetCategoryById_thenThrowResourceNotFoundException() {
        // Arrange
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, never()).toDto(any(Category.class));
    }

    @Test
    public void whenCreateCategory_thenReturnCategoryDto() {
        // Arrange
        CategoryCreateUpdateRequest request = new CategoryCreateUpdateRequest();
        request.setCategoryName("Test Category");

        Category category = new Category();
        category.setCategoryName(request.getCategoryName());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(category.getCategoryName());

        when(categoryRepository.existsByCategoryName(request.getCategoryName())).thenReturn(false);
        when(categoryMapper.toEntityFromRequest(request)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        CategoryDto result = categoryService.createCategory(request);

        // Assert
        assertNotNull(result);
        assertEquals(request.getCategoryName(), result.getCategoryName());

        verify(categoryRepository, times(1)).existsByCategoryName(request.getCategoryName());
        verify(categoryMapper, times(1)).toEntityFromRequest(request);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    public void givenAlreadyCategoryName_whenCreateCategory_thenThrowsResourceAlreadyExistException() {
        // Arrange
        CategoryCreateUpdateRequest request = new CategoryCreateUpdateRequest();
        request.setCategoryName("Test Category");

        when(categoryRepository.existsByCategoryName(request.getCategoryName())).thenReturn(true);

        // Act & Assert
        assertThrows(ResourceAlreadyExistException.class, () -> categoryService.createCategory(request));
        verify(categoryRepository, times(1)).existsByCategoryName(request.getCategoryName());
        verify(categoryMapper, never()).toEntityFromRequest(request);
    }

    @Test
    public void givenUpdateParentIdNotNull_whenUpdateCategory_thenReturnCategoryDtoWithNewParent2(){
        // Arrange
        Long categoryId = 1L;
        Long parentId = 2L;
        CategoryCreateUpdateRequest request = new CategoryCreateUpdateRequest();
        request.setCategoryName("Updated Category");
        request.setParentId(parentId);

        Category category = new Category();
        category.setId(categoryId);
        category.setCategoryName("Old Category");
        category.setParentCategory(null);

        Category parentCategory = new Category();
        parentCategory.setId(parentId);
        parentCategory.setCategoryName("Parent Category");
        parentCategory.setLayerNum(0L);

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setCategoryName(request.getCategoryName());

        CategoryDto returnCategoryDto = new CategoryDto();
        returnCategoryDto.setId(categoryId);
        returnCategoryDto.setCategoryName(updatedCategory.getCategoryName());
        returnCategoryDto.setParentId(parentId);
        returnCategoryDto.setLayerNum(parentCategory.getLayerNum() + 1);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByCategoryName(request.getCategoryName())).thenReturn(false);
        when(categoryRepository.findById(parentId)).thenReturn(Optional.of(parentCategory));
        when(categoryMapper.updateEntityFromRequest(request, category)).thenReturn(updatedCategory);
        when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(returnCategoryDto);

        // Act
        CategoryDto result = categoryService.updateCategory(categoryId, request);

        // Assert
        assertNotNull(result);
        assertEquals(request.getCategoryName(), updatedCategory.getCategoryName());
        assertNotNull(updatedCategory.getParentCategory());
        // Verify that the parent category and layer number are updated
        assertEquals(parentId, updatedCategory.getParentCategory().getId());
        assertEquals(parentCategory.getLayerNum() + 1, updatedCategory.getLayerNum());

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).existsByCategoryName(request.getCategoryName());
        verify(categoryMapper).updateEntityFromRequest(eq(request), any(Category.class));
        verify(categoryRepository).findById(2L); // Ensure parent category is fetched
        verify(categoryRepository).save(updatedCategory);
    }

    @Test
    public void whenUpdateCategory_thenReturnCategoryDto() {
        // Arrange
        Long categoryId = 1L;
        CategoryCreateUpdateRequest request = new CategoryCreateUpdateRequest();
        request.setCategoryName("Updated Category");

        Category category = new Category();
        category.setId(categoryId);
        category.setCategoryName("Old Category");

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setCategoryName(request.getCategoryName());

        CategoryDto returnCategoryDto = new CategoryDto();
        returnCategoryDto.setId(categoryId);
        returnCategoryDto.setCategoryName(updatedCategory.getCategoryName());

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByCategoryName(request.getCategoryName())).thenReturn(false);
        when(categoryMapper.updateEntityFromRequest(request, category)).thenReturn(updatedCategory);
        when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(returnCategoryDto);

        // Act
        CategoryDto result = categoryService.updateCategory(categoryId, request);

        // Assert
        assertNotNull(result);
        assertEquals(request.getCategoryName(), result.getCategoryName());
    }

    @Test
    public void givenNotExistsCategoryId_whenUpdateCategory_ThrowsResourceNotFoundException() {
        // Arrange
        Long categoryId = 1L;
        CategoryCreateUpdateRequest request = new CategoryCreateUpdateRequest();
        request.setCategoryName("Updated Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(categoryId, request));
    }

    @Test
    public void givenExistingCategoryAndDuplicateName_whenUpdateCategory_thenThrowsResourceAlreadyExistException() {
        // Arrange
        Long categoryId = 1L;
        CategoryCreateUpdateRequest request = new CategoryCreateUpdateRequest();
        request.setCategoryName("Updated Category");

        Category category = new Category();
        category.setId(categoryId);
        category.setCategoryName("Old Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByCategoryName(request.getCategoryName())).thenReturn(true);

        // Act & Assert
        assertThrows(ResourceAlreadyExistException.class, () -> categoryService.updateCategory(categoryId, request));
    }

    @Test
    public void whenDeleteCategory_ReturnNone() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        assertDoesNotThrow(() -> categoryService.deleteCategory(categoryId));

        // Assert
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void giveNotExistCategoryId_whenDeleteCategory_ThrowsResourceNotFoundException() {
        // Arrange
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(categoryId));
    }
}