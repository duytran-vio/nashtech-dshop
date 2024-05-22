package com.nashtech.dshop_api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.dshop_api.data.entities.Category;
import com.nashtech.dshop_api.data.entities.Product;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.repositories.ProductRepository;
import com.nashtech.dshop_api.dto.requests.Product.ProductCreateUpdateRequest;
import com.nashtech.dshop_api.dto.responses.Product.ProductDetailDto;
import com.nashtech.dshop_api.dto.responses.Product.ProductElementDto;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.ProductMapper;
import com.nashtech.dshop_api.services.CategoryService;
import com.nashtech.dshop_api.services.ProductService;
import com.nashtech.dshop_api.services.UserService;


@Service
public class ProductServiceImpl implements ProductService{

    static final String STOCK_EXCEPTION_MESSAGE = "Stock must be greater than or equal to current stock";
    
    private ProductRepository productRepository;
    private ProductMapper mapper;
    private CategoryService categoryService;
    private UserService userService;
    
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, 
                                ProductMapper mapper,
                                CategoryService categoryService,
                                UserService userService) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Override
    public List<ProductElementDto> getAllProducts() {
        var products = productRepository.findAllByIsDeletedFalse()
                                        .stream()
                                        .map(mapper::toProductElementDto)
                                        .toList();
        return products;
    }

    public Product getProductEntityById(Long id) {
        return productRepository.findByIdAndIsDeletedFalse(id)
                                .orElseThrow(() -> new ResourceNotFoundException(Product.class.getSimpleName(), "id", id));
    }

    @Override
    public ProductDetailDto getProductById(Long id) {
        var product = getProductEntityById(id);
        return mapper.toProductDetailDto(product);
    }

    @Override
    public ProductDetailDto createProduct(ProductCreateUpdateRequest productCreateRequest) {
        if (productRepository.existsByProductNameAndIsDeletedFalse(productCreateRequest.getProductName())) {
            throw new ResourceAlreadyExistException(Product.class.getSimpleName(), 
                                                    "product name", 
                                                    productCreateRequest.getProductName());
        }

        User createUser = userService.getUserEntityById(productCreateRequest.getCreateUserId());
        Category category = categoryService.getCategoryEntityById(productCreateRequest.getCategoryId());

        var product = mapper.toEntityFromCreateRequest(productCreateRequest);
        product.setCreateUser(createUser);
        product.setCategory(category);
        product = productRepository.save(product);
        return mapper.toProductDetailDto(product);
    }

    @Override
    public ProductDetailDto updateProduct(Long id, ProductCreateUpdateRequest productUpdateRequest) {
        var product = this.getProductEntityById(id);
        if (!product.getProductName().equals(productUpdateRequest.getProductName()) 
            && productRepository.existsByProductNameAndIsDeletedFalse(productUpdateRequest.getProductName())) {
            throw new ResourceAlreadyExistException(Product.class.getSimpleName(), 
                                                    "product name", 
                                                    productUpdateRequest.getProductName());
        }


        if (productUpdateRequest.getStock() < product.getStock()){
            throw new IllegalArgumentException(STOCK_EXCEPTION_MESSAGE);
        }
        else{
            product.setStock(productUpdateRequest.getStock());
        }

        if (productUpdateRequest.getCategoryId() != null 
            && product.getCategory().getId() != productUpdateRequest.getCategoryId()) {
            Category category = categoryService.getCategoryEntityById(productUpdateRequest.getCategoryId());
            product.setCategory(category);
        }

        mapper.toEntityFromUpdateRequest(product, productUpdateRequest);
        product = productRepository.save(product);
        return mapper.toProductDetailDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        var product = this.getProductEntityById(id);
        product.setIsDeleted(true);
        productRepository.save(product);
    }

}
