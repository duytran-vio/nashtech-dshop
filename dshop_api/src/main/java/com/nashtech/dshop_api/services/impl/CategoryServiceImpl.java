package com.nashtech.dshop_api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.dshop_api.data.entities.Category;
import com.nashtech.dshop_api.data.repositories.CategoryRepository;
import com.nashtech.dshop_api.dto.requests.CategoryCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.CategoryDto;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.CategoryMapper;
import com.nashtech.dshop_api.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                                CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        var categories = categoryRepository.findAll()
                                            .stream()
                                            .map(mapper::toDto)
                                            .toList();
        return categories;
    }

    public Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(Category.class.getSimpleName(), "id", id));
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        var category = getCategoryEntityById(id);
        return mapper.toDto(category);
    }

    private void updateParentCategory(Category category, Long parentId) {
        if (parentId == null) {
            category.setParentCategory(null);
            category.setLayerNum(Long.valueOf(0));
        }
        else{
            Category parentCategory = this.getCategoryEntityById(parentId);
            category.setParentCategory(parentCategory);
            category.setLayerNum(parentCategory.getLayerNum() + 1);
        }
    }

    @Override
    public CategoryDto createCategory(CategoryCreateUpdateRequest categoryRequest) {
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new ResourceAlreadyExistException(Category.class.getSimpleName(), "category name", categoryRequest.getCategoryName());
        }

        Category category = mapper.toEntityFromRequest(categoryRequest);
        updateParentCategory(category, categoryRequest.getParentId());

        category = categoryRepository.save(category);
        return mapper.toDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryCreateUpdateRequest categoryRequest) {
        Category category = getCategoryEntityById(id);

        if (!category.getCategoryName().equals(categoryRequest.getCategoryName())
                && categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new ResourceAlreadyExistException(Category.class.getSimpleName(), "category name",
                    categoryRequest.getCategoryName());
        }

        category = mapper.updateEntityFromRequest(categoryRequest, category);

        if ((category.getParentCategory() != null
                && categoryRequest.getParentId() != category.getParentCategory().getId())
                || (category.getParentCategory() == null && categoryRequest.getParentId() != null)) {
            updateParentCategory(category, categoryRequest.getParentId());
        }

        category = categoryRepository.save(category);
        return mapper.toDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        var category = getCategoryEntityById(id);
        categoryRepository.delete(category);
    }
}
