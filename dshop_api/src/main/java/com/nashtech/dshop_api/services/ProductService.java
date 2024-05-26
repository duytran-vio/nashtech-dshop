package com.nashtech.dshop_api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nashtech.dshop_api.data.entities.Product;
import com.nashtech.dshop_api.dto.requests.Product.ProductCreateUpdateRequest;
import com.nashtech.dshop_api.dto.requests.Product.ProductGetRequest;
import com.nashtech.dshop_api.dto.responses.Product.ProductDetailDto;
import com.nashtech.dshop_api.dto.responses.Product.ProductElementDto;

public interface ProductService {
    public Page<ProductElementDto> getAllProductsByCriterion(ProductGetRequest productGetRequest, Pageable pageable);
    public ProductDetailDto getProductById(Long id);
    public ProductDetailDto createProduct(ProductCreateUpdateRequest productCreateRequest);
    public ProductDetailDto updateProduct(Long id, ProductCreateUpdateRequest productUpdateRequest);
    public void deleteProduct(Long id);
    public Product getProductEntityById(Long id);
    public boolean isProductExist(Long id);
    public void updateNewReviewRating(Product product, Long rating);
}
