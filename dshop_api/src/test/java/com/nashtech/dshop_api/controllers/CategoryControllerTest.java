package com.nashtech.dshop_api.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashtech.dshop_api.data.entities.Category;
import com.nashtech.dshop_api.data.entities.Category_;
import com.nashtech.dshop_api.dto.requests.CategoryCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.CategoryDto;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.services.CategoryService;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    List<CategoryDto> categories;
    CategoryCreateUpdateRequest createUpdateRequest;
    CategoryDto newCategory;
    Long notExistCategoryId;
    ResourceNotFoundException resourceNotFoundException;
    ResourceAlreadyExistException resourceAlreadyExistException;


    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    private final String controllerEndPoint = "/categories";

    String categoryUrl;

    @BeforeEach
    void setUp() {

        categoryUrl = baseUrl + controllerEndPoint;

        categories = new ArrayList<>(); 

        CategoryDto category1 = new CategoryDto();
        category1.setId(1L);
        category1.setCategoryName("category1");

        CategoryDto category2 = new CategoryDto();
        category2.setId(2L);
        category2.setCategoryName("category2");

        categories.add(category1);
        categories.add(category2);

        createUpdateRequest = new CategoryCreateUpdateRequest();
        createUpdateRequest.setCategoryName("new Category");

        newCategory = new CategoryDto();
        newCategory.setId(3L);
        newCategory.setCategoryName("new Category");

        notExistCategoryId = 3L;
        resourceNotFoundException = new ResourceNotFoundException(Category.class.getSimpleName(), Category_.ID, notExistCategoryId);
        resourceAlreadyExistException = new ResourceAlreadyExistException(Category.class.getSimpleName(), Category_.CATEGORY_NAME, createUpdateRequest.getCategoryName());
    }

    @Test
    public void testGetAllCategoriesSuccess() throws Exception {
        //Arrange
        when(categoryService.getAllCategories()).thenReturn(categories);

        //Act & Assert
        this.mockMvc.perform(get(this.categoryUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(categories.size()))
                .andExpect(jsonPath("$[0].id").value(categories.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(categories.get(1).getId()));
    }

    @Test 
    public void testGetAllCategoriesEmpty() throws Exception {
        //Arrange
        when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());

        //Act & Assert
        this.mockMvc.perform(get(this.categoryUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetCategoryByIdSuccess() throws Exception {
        //Arrange
        when(categoryService.getCategoryById(1L)).thenReturn(categories.get(0));

        //Act & Assert
        this.mockMvc.perform(get(this.categoryUrl + "/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categories.get(0).getId()))
                .andExpect(jsonPath("$.categoryName").value(categories.get(0).getCategoryName()));
    }

    @Test
    public void testGetCategoryByIdNotFound() throws Exception {
        //Arrange
        when(categoryService.getCategoryById(notExistCategoryId)).thenThrow(resourceNotFoundException);

        //Act & Assert
        String url = this.categoryUrl + "/" + notExistCategoryId.toString();
        this.mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(resourceNotFoundException.getMessage()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateCategorySuccess() throws Exception{
        //Arrange
        String json = objectMapper.writeValueAsString(createUpdateRequest);
        when(categoryService.createCategory(createUpdateRequest)).thenReturn(newCategory);
        //Act & Assert
        this.mockMvc.perform(post(this.categoryUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryName").value(createUpdateRequest.getCategoryName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateCategoryBadRequest() throws Exception{
        //Arrange
        createUpdateRequest.setCategoryName("a"); // category name is too short < 3 characters
        String json = objectMapper.writeValueAsString(createUpdateRequest);
        //Act & Assert
        this.mockMvc.perform(post(this.categoryUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("Validation Error"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateCategoryWithAlreadyExistCategoryName() throws Exception{
        //Arrange
        createUpdateRequest.setCategoryName(categories.get(0).getCategoryName());
        String json = objectMapper.writeValueAsString(createUpdateRequest);
        when(categoryService.createCategory(createUpdateRequest)).thenThrow(resourceAlreadyExistException);
        //Act & Assert
        this.mockMvc.perform(post(this.categoryUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.message").value(resourceAlreadyExistException.getMessage()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateCategorySuccess() throws Exception{
        //Arrange
        String json = objectMapper.writeValueAsString(createUpdateRequest);
        categories.get(0).setCategoryName(createUpdateRequest.getCategoryName());
        when(categoryService.updateCategory(1L, createUpdateRequest)).thenReturn(categories.get(0));
        //Act & Assert
        this.mockMvc.perform(put(this.categoryUrl + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categories.get(0).getId()))
                .andExpect(jsonPath("$.categoryName").value(createUpdateRequest.getCategoryName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateCategoryNotFound() throws Exception{
        //Arrange
        String json = objectMapper.writeValueAsString(createUpdateRequest);
        when(categoryService.updateCategory(notExistCategoryId, createUpdateRequest)).thenThrow(resourceNotFoundException);
        //Act & Assert
        this.mockMvc.perform(put(this.categoryUrl + "/" + notExistCategoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(resourceNotFoundException.getMessage()));
    }

    @Test
    @WithMockUser (username = "admin", roles = {"ADMIN"})
    public void testUpdateCategoryBadRequest() throws Exception{
        //Arrange
        createUpdateRequest.setCategoryName("a"); // category name is too short < 3 characters
        String json = objectMapper.writeValueAsString(createUpdateRequest);
        //Act & Assert
        this.mockMvc.perform(put(this.categoryUrl + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("Validation Error"));
    }
}