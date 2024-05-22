package com.nashtech.dshop_api.services;

import java.util.List;

import com.nashtech.dshop_api.dto.requests.Product.ProductCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.Product.ProductDetailDto;
import com.nashtech.dshop_api.dto.responses.Product.ProductElementDto;

public interface ProductService {
    public List<ProductElementDto> getAllProducts();
    public ProductDetailDto getProductById(Long id);
    public ProductDetailDto createProduct(ProductCreateUpdateRequest productCreateRequest);
    // public ProductDetailDto updateProduct(Long id, ProductUpdateRequest productUpdateRequest);
}
