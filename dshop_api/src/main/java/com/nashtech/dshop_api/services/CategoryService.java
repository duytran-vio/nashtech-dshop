package com.nashtech.dshop_api.services;

import java.util.List;

import com.nashtech.dshop_api.data.entities.Category;
import com.nashtech.dshop_api.dto.requests.CategoryCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.CategoryDto;

public interface CategoryService {
    public List<CategoryDto> getAllCategories();
    public CategoryDto getCategoryById(Long id);
    public CategoryDto createCategory(CategoryCreateUpdateRequest categoryDto);
    public CategoryDto updateCategory(Long id, CategoryCreateUpdateRequest categoryDto);
    public void deleteCategory(Long id);
    public Category getCategoryEntityById(Long id);
}
