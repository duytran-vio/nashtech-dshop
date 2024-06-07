package com.nashtech.dshop_api.services.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.dshop_api.data.entities.Category;
import com.nashtech.dshop_api.data.entities.Category_;
import com.nashtech.dshop_api.data.entities.Image;
import com.nashtech.dshop_api.data.entities.Product;
import com.nashtech.dshop_api.data.entities.Product_;
import com.nashtech.dshop_api.data.entities.StatusType;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.repositories.ProductRepository;
import com.nashtech.dshop_api.dto.requests.Product.ProductBuyRequest;
import com.nashtech.dshop_api.dto.requests.Product.ProductCreateUpdateRequest;
import com.nashtech.dshop_api.dto.requests.Product.ProductGetRequest;
import com.nashtech.dshop_api.dto.responses.Product.ProductDetailDto;
import com.nashtech.dshop_api.dto.responses.Product.ProductElementDto;
import com.nashtech.dshop_api.exceptions.ResourceAlreadyExistException;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.ProductMapper;
import com.nashtech.dshop_api.services.CategoryService;
import com.nashtech.dshop_api.services.ImageService;
import com.nashtech.dshop_api.services.ProductService;
import com.nashtech.dshop_api.services.UserService;
import com.nashtech.dshop_api.utils.Constant;


@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService{

    static final String STOCK_EXCEPTION_MESSAGE = "Input stock value must be greater than or equal to current stock";
    
    private ProductRepository productRepository;
    private ProductMapper mapper;
    private CategoryService categoryService;
    private UserService userService;
    private ImageService imageService;

    public static Specification<Product> likeName(String name) {
        return (root, query, builder) -> builder.like(root.get(Product_.productName), "%" + name + "%");
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, builder) -> builder.equal(root.get(Product_.CATEGORY).get(Category_.ID), categoryId);
    }

    public static Specification<Product> hasStatusType(StatusType status) {
        return (root, query, builder) -> builder.equal(root.get(Product_.status), status);
    }

    public static Specification<Product> isFeatured(Boolean isFeatured) {
        return (root, query, builder) -> builder.equal(root.get(Product_.isFeatured), isFeatured);
    }

    public static Specification<Product> isDeletedFalse(){
        return (root, query, builder) -> builder.isFalse(root.get(Product_.isDeleted));
    }
    
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, 
                                ProductMapper mapper,
                                CategoryService categoryService,
                                UserService userService,
                                ImageService imageService) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.categoryService = categoryService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public Page<ProductElementDto> getAllProductsByCriterion(ProductGetRequest productGetRequest, Pageable pageable) {

        Specification<Product> spec = Specification.where(isDeletedFalse());
        if (productGetRequest.getProductName() != null) {
            spec = spec.and(likeName(productGetRequest.getProductName()));
        }

        if (productGetRequest.getCategoryId() != null) {
            spec = spec.and(hasCategory(productGetRequest.getCategoryId()));
        }

        if (productGetRequest.getStatus() != null) {
            spec = spec.and(hasStatusType(productGetRequest.getStatus()));
        }

        if (productGetRequest.getIsFeatured() != null) {
            spec = spec.and(isFeatured(productGetRequest.getIsFeatured()));
        }

        var products = productRepository.findAll(spec, pageable)
                                        .map(mapper::toProductElementDto);
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

    public void updateProductImages(Product product, List<Long> imageIds) {
        List<Image> images = new LinkedList<>();
        for(Long imageId : imageIds){
            Image image = imageService.getEntityById(imageId);
            images.add(image);
        }
        product.setImages(images);
    }

    @Override
    @Transactional
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
        updateProductImages(product, productCreateRequest.getImageIds());
        product = productRepository.save(product);
        return mapper.toProductDetailDto(product);
    }

    @Override
    @Transactional
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

        updateProductImages(product, productUpdateRequest.getImageIds());

        mapper.toEntityFromUpdateRequest(product, productUpdateRequest);
        product = productRepository.save(product);
        return mapper.toProductDetailDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        var product = this.getProductEntityById(id);
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    @Override
    public boolean isProductExist(Long id) {
        return productRepository.existsByIdAndIsDeletedFalse(id);
    }

    @Override 
    @Transactional
    public void updateNewReviewRating(Product product, Long rating){
        var newRating = (product.getAvgRating() * product.getReviewNum() + rating) / (product.getReviewNum() + 1);
        product.setAvgRating(newRating);
        product.setReviewNum(product.getReviewNum() + 1);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public ProductDetailDto buyProduct(ProductBuyRequest productBuyRequest) {
        var product = this.getProductEntityById(productBuyRequest.getId());
        if (product.getStock() < productBuyRequest.getQuantity()){
            throw new IllegalArgumentException(Constant.QUANTITY_EXCEED_STOCK);
        }
        product.setStock(product.getStock() - productBuyRequest.getQuantity());
        product.setSoldNum(product.getSoldNum() + productBuyRequest.getQuantity());
        product = productRepository.save(product);
        return mapper.toProductDetailDto(product);
    }

}
