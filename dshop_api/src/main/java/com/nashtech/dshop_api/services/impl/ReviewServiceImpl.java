package com.nashtech.dshop_api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.dshop_api.data.entities.Product;
import com.nashtech.dshop_api.data.entities.Review;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.repositories.ReviewRepository;
import com.nashtech.dshop_api.dto.responses.ReviewDto;
import com.nashtech.dshop_api.exceptions.ResourceNotFoundException;
import com.nashtech.dshop_api.mappers.ReviewMapper;
import com.nashtech.dshop_api.services.ProductService;
import com.nashtech.dshop_api.services.ReviewService;
import com.nashtech.dshop_api.services.UserService;

@Service
public class ReviewServiceImpl implements ReviewService{
    
    private ReviewRepository reviewRepository;
    private ReviewMapper mapper;
    private UserService userService;
    private ProductService productService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, 
                            ReviewMapper mapper,
                            UserService userService,
                            ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        User user = userService.getUserEntityById(reviewDto.getUserId());
        Product product = productService.getProductEntityById(reviewDto.getProductId());

        Review review = mapper.toEntity(reviewDto);
        review.setUser(user);
        review.setProduct(product);
        return mapper.toDto(reviewRepository.save(review));
    }

    @Override
    public List<ReviewDto> getReviewsByProductId(Long productId) {
        if (!productService.isProductExist(productId)) {
            throw new ResourceNotFoundException(Product.class.getSimpleName(), "id", productId);
        }
        var reviews = reviewRepository.findAllByProductId(productId)
                                        .stream()
                                        .map(mapper::toDto)
                                        .toList();
        return reviews;
    }

    @Override
    public List<ReviewDto> getReviewsByUserId(Long userId) {
        if (!userService.isUserExist(userId)) {
            throw new ResourceNotFoundException(User.class.getSimpleName(), "id", userId);
        }
        var reviews = reviewRepository.findAllByUserId(userId)
                                        .stream()
                                        .map(mapper::toDto)
                                        .toList();
        return reviews;
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        var reviews = reviewRepository.findAll()
                                        .stream()
                                        .map(mapper::toDto)
                                        .toList();
        return reviews;
    }
}
