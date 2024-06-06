package com.nashtech.dshop_api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashtech.dshop_api.data.entities.Product;
import com.nashtech.dshop_api.data.entities.Product_;
import com.nashtech.dshop_api.data.entities.StatusType;
import com.nashtech.dshop_api.dto.requests.Product.ProductBuyRequest;
import com.nashtech.dshop_api.dto.requests.Product.ProductCreateUpdateRequest;
import com.nashtech.dshop_api.dto.requests.Product.ProductGetRequest;
import com.nashtech.dshop_api.dto.responses.Product.ProductDetailDto;
import com.nashtech.dshop_api.dto.responses.Product.ProductElementDto;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.services.ProductService;
import com.nashtech.dshop_api.utils.Constant;

import lombok.With;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    private final String controllerEndPoint = "/products";

    private String url;

    @MockBean
    private ProductService productService;

    List<ProductElementDto> productElementDtoList;
    ProductBuyRequest productBuyRequest;
    ProductDetailDto productDetailDto;
    ProductCreateUpdateRequest productCreateUpdateRequest;
    ResourceAlreadyExistException resourceAlreadyExistException;
    ResourceNotFoundException resourceNotFoundException;
    Long notExistProductId;
    ProductGetRequest productGetRequest;

    @BeforeEach
    void setUp() {
        url = baseUrl + controllerEndPoint;

        productElementDtoList = new ArrayList<>();

        ProductElementDto productElementDto1 = new ProductElementDto();
        productElementDto1.setId(1L);
        productElementDto1.setProductName("Product 1");
        productElementDto1.setPrice(100.0F);
        productElementDto1.setAvgRating(4.5F);
        productElementDto1.setSoldNum(100L);
        productElementDto1.setCategoryId(1L);
        productElementDto1.setStatus(StatusType.ACTIVE);
        productElementDto1.setIsFeatured(true);
        productElementDto1.setStock(100L);
        

        ProductElementDto productElementDto2 = new ProductElementDto();
        productElementDto2.setId(2L);
        productElementDto2.setProductName("Product 2");
        productElementDto2.setPrice(200.0F);
        productElementDto2.setAvgRating(4.0F);
        productElementDto2.setSoldNum(200L);
        productElementDto2.setCategoryId(2L);
        productElementDto2.setStatus(StatusType.ACTIVE);
        productElementDto2.setIsFeatured(false);
        productElementDto2.setStock(200L);

        ProductElementDto productElementDto3 = new ProductElementDto();
        productElementDto2.setId(3L);
        productElementDto2.setProductName("Product 3");
        productElementDto2.setPrice(200.0F);
        productElementDto2.setAvgRating(4.0F);
        productElementDto2.setSoldNum(200L);
        productElementDto2.setCategoryId(2L);
        productElementDto2.setStatus(StatusType.ACTIVE);
        productElementDto2.setIsFeatured(false);
        productElementDto2.setStock(200L);

        productElementDtoList.add(productElementDto1);
        productElementDtoList.add(productElementDto2);
        productElementDtoList.add(productElementDto3);

        productDetailDto = new ProductDetailDto();
        productDetailDto.setId(1L);
        productDetailDto.setProductName("New Product");
        productDetailDto.setPrice(100.0F);
        productDetailDto.setAvgRating(4.5F);
        productDetailDto.setSoldNum(100L);
        productDetailDto.setCategoryId(1L);
        productDetailDto.setStatus(StatusType.ACTIVE);
        productDetailDto.setIsFeatured(true);
        productDetailDto.setStock(100L);

        productBuyRequest = new ProductBuyRequest();
        productBuyRequest.setId(1L);
        productBuyRequest.setQuantity(1L);

        productCreateUpdateRequest = new ProductCreateUpdateRequest();
        productCreateUpdateRequest.setProductName("New Product");  
        productCreateUpdateRequest.setPrice(100.0F);
        productCreateUpdateRequest.setStock(100L);

        resourceAlreadyExistException = new ResourceAlreadyExistException(Product.class.getSimpleName(), 
                                                    Product_.PRODUCT_NAME, 
                                                    "Product 1");

        notExistProductId = 4L;
        resourceNotFoundException = new ResourceNotFoundException(Product.class.getSimpleName(), Product_.ID, notExistProductId);

        productGetRequest = new ProductGetRequest();
        productGetRequest.setStatus(StatusType.ACTIVE);
    }

    @Test
    @WithMockUser(username = "cus", roles = {"CUSTOMER"})
    void testBuyProductSuccess() throws Exception{
        //Arrange
        Long remainingStock = productDetailDto.getStock() - productBuyRequest.getQuantity();
        productDetailDto.setStock(remainingStock);
        when(productService.buyProduct(productBuyRequest)).thenReturn(productDetailDto);

        String json = objectMapper.writeValueAsString(productBuyRequest);
        //Act & Assert
        this.mockMvc.perform(post(url + "/buy")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.stock").value(remainingStock));
    }

    @Test
    @WithMockUser(username = "cus", roles = {"CUSTOMER"})
    void testBuyProductFail() throws Exception{
        //Arrange
        when(productService.buyProduct(productBuyRequest)).thenThrow(new IllegalArgumentException(Constant.QUANTITY_EXCEED_STOCK));

        String json = objectMapper.writeValueAsString(productBuyRequest);
        //Act & Assert
        this.mockMvc.perform(post(url + "/buy")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(Constant.QUANTITY_EXCEED_STOCK));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateProductSuccess() throws Exception{
        //Arrange
        when(productService.createProduct(productCreateUpdateRequest)).thenReturn(productDetailDto);
        String json = objectMapper.writeValueAsString(productCreateUpdateRequest);
        //Act & Assert
        this.mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.productName").value(productCreateUpdateRequest.getProductName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateProductAlreadyExistException() throws Exception{
        //Arrange
        productCreateUpdateRequest.setProductName("Product 1");  
        when(productService.createProduct(productCreateUpdateRequest)).thenThrow(resourceAlreadyExistException);
        String json = objectMapper.writeValueAsString(productCreateUpdateRequest);
        //Act & Assert
        this.mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").value(resourceAlreadyExistException.getMessage()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateProductBadRequest() throws Exception{
        //Arrange
        productCreateUpdateRequest.setProductName("");  
        String json = objectMapper.writeValueAsString(productCreateUpdateRequest);
        //Act & Assert
        this.mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteProduct() throws Exception{
        //Arrange
        Long id = 1L;
        //Act & Assert
        this.mockMvc.perform(delete(url + "/" + id)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(Constant.DELETE_SUCCESS_MSG));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteProductNotFound() throws Exception{
        //Arrange
        doThrow(resourceNotFoundException).when(productService).deleteProduct(notExistProductId);
        //Act & Assert
        this.mockMvc.perform(delete(url + "/" + notExistProductId)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.message").value(resourceNotFoundException.getMessage()));
    }

    @Test
    void testGetAllProductsByCriterionSucess() throws Exception{
        //Arrange

        Pageable pageable = PageRequest.of(0, 20);
        PageImpl<ProductElementDto> productPage = new PageImpl<>(productElementDtoList, pageable, productElementDtoList.size());
        when(productService.getAllProductsByCriterion(any(ProductGetRequest.class), any(Pageable.class))).thenReturn(productPage);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("status", "ACTIVE");
        params.add("page", "0");
        params.add("size", "20");

        //Act & Assert
        this.mockMvc.perform(get(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .params(params))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", Matchers.hasSize(productElementDtoList.size())))
                    .andExpect(jsonPath("$.content[0].productName").value(productElementDtoList.get(0).getProductName()))
                    .andExpect(jsonPath("$.content[1].productName").value(productElementDtoList.get(1).getProductName()));
    }

    @Test
    void testGetProductById() throws Exception {
        //Arrange
        Long id = 1L;
        when(productService.getProductById(id)).thenReturn(productDetailDto);
        //Act & Assert
        this.mockMvc.perform(get(url + "/" + id)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.productName").value(productDetailDto.getProductName()));
    }

    @Test
    void testGetProductByIdNotFound() throws Exception {
        //Arrange
        when(productService.getProductById(notExistProductId)).thenThrow(resourceNotFoundException);
        //Act & Assert
        this.mockMvc.perform(get(url + "/" + notExistProductId)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.message").value(resourceNotFoundException.getMessage()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateProductSuccess() throws Exception {
        //Arrange
        Long id = 1L;
        when(productService.updateProduct(id, productCreateUpdateRequest)).thenReturn(productDetailDto);
        String json = objectMapper.writeValueAsString(productCreateUpdateRequest);
        //Act & Assert
        this.mockMvc.perform(put(url + "/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.productName").value(productDetailDto.getProductName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateProductNotFound() throws Exception {
        //Arrange
        when(productService.updateProduct(notExistProductId, productCreateUpdateRequest)).thenThrow(resourceNotFoundException);
        String json = objectMapper.writeValueAsString(productCreateUpdateRequest);
        //Act & Assert
        this.mockMvc.perform(put(url + "/" + notExistProductId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value(resourceNotFoundException.getMessage()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateProductBadRequest() throws Exception {
        //Arrange
        productCreateUpdateRequest.setProductName("");
        Long id = 1L;
        String json = objectMapper.writeValueAsString(productCreateUpdateRequest);
        //Act & Assert
        this.mockMvc.perform(put(url + "/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateProductAlreadyExistProductName() throws Exception {
        //Arrange
        Long id = 1L;
        when(productService.updateProduct(id, productCreateUpdateRequest)).thenThrow(resourceAlreadyExistException);
        String json = objectMapper.writeValueAsString(productCreateUpdateRequest);
        //Act & Assert
        this.mockMvc.perform(put(url + "/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").value(resourceAlreadyExistException.getMessage()));
    }
}
