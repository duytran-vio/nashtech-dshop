package com.nashtech.dshop_api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.dshop_api.data.entities.Category;
import com.nashtech.dshop_api.data.entities.Image;
import com.nashtech.dshop_api.data.repositories.CategoryRepository;
import com.nashtech.dshop_api.dto.requests.CategoryCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.CategoryDto;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.CategoryMapper;
import com.nashtech.dshop_api.services.CategoryService;
import com.nashtech.dshop_api.services.ImageService;


@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService{
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    private final ImageService imageService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                                CategoryMapper mapper, ImageService imageService) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.imageService = imageService;
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
            return;
        }
        if (category.getParentCategory() != null && category.getParentCategory().getId() == parentId) {
            return;
        }
        Category parentCategory = this.getCategoryEntityById(parentId);
        category.setParentCategory(parentCategory);
        category.setLayerNum(parentCategory.getLayerNum() + 1);
    }
    
    private void updateCategoryImage(Category category, Long imageId) {
        if (imageId == null) {
            category.setImage(null);
            return;
        }
        if (category.getImage() != null && category.getImage().getId() == imageId) {
            return;
        }
        Image image = imageService.getEntityById(imageId);
        category.setImage(image);
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryCreateUpdateRequest categoryRequest) {
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new ResourceAlreadyExistException(Category.class.getSimpleName(), "category name", categoryRequest.getCategoryName());
        }

        Category category = mapper.toEntityFromRequest(categoryRequest);
        updateParentCategory(category, categoryRequest.getParentId());
        
        updateCategoryImage(category, categoryRequest.getImageId());

        category = categoryRepository.save(category);
        return mapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryCreateUpdateRequest categoryRequest) {
        Category category = getCategoryEntityById(id);

        if (!category.getCategoryName().equals(categoryRequest.getCategoryName())
                && categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new ResourceAlreadyExistException(Category.class.getSimpleName(), "category name",
                    categoryRequest.getCategoryName());
        }

        category = mapper.updateEntityFromRequest(categoryRequest, category);

        updateParentCategory(category, categoryRequest.getParentId());

        updateCategoryImage(category, categoryRequest.getImageId());

        category = categoryRepository.save(category);
        return mapper.toDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        var category = getCategoryEntityById(id);
        categoryRepository.delete(category);
    }
}
