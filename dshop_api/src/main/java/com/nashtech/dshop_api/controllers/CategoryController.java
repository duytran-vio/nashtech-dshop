package com.nashtech.dshop_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.dshop_api.dto.requests.CategoryCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.CategoryDto;
import com.nashtech.dshop_api.services.CategoryService;
import com.nashtech.dshop_api.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController{
    
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok()
                            .body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable("id") Long id){
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok()
                            .body(category);
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryCreateUpdateRequest categoryDto){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("id") Long id, 
                                                    @Valid @RequestBody CategoryCreateUpdateRequest categoryDto){
        CategoryDto category = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok()
                            .body(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok()
                            .body(Constant.DELETE_SUCCESS_MSG);
    }
}
