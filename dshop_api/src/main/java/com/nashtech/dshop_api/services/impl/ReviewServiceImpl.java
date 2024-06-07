package com.nashtech.dshop_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.dshop_api.data.entities.Product;
import com.nashtech.dshop_api.data.entities.Product_;
import com.nashtech.dshop_api.data.entities.Review;
import com.nashtech.dshop_api.data.entities.Review_;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.data.entities.User_;
import com.nashtech.dshop_api.data.repositories.ReviewRepository;
import com.nashtech.dshop_api.dto.requests.ReviewGetRequest;
import com.nashtech.dshop_api.dto.responses.ReviewDto;
import com.nashtech.dshop_api.mappers.ReviewMapper;
import com.nashtech.dshop_api.services.ProductService;
import com.nashtech.dshop_api.services.ReviewService;
import com.nashtech.dshop_api.services.UserService;

@Service
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{
    
    private ReviewRepository reviewRepository;
    private ReviewMapper mapper;
    private UserService userService;
    private ProductService productService;

    public static Specification<Review> belongProductId(Long productId) {
        return (root, query, builder) -> builder.equal(root.get(Review_.PRODUCT).get(Product_.ID), productId);
    }

    public static Specification<Review> belongUserId(Long userId) {
        return (root, query, builder) -> builder.equal(root.get(Review_.USER).get(User_.ID), userId);
    }

    public static Specification<Review> hasRating(Long rating) {
        return (root, query, builder) -> builder.equal(root.get(Review_.rating), rating);
    }

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
    @Transactional
    public ReviewDto createReview(ReviewDto reviewDto) {
        User user = userService.getUserEntityById(reviewDto.getUserId());
        Product product = productService.getProductEntityById(reviewDto.getProductId());

        Review review = mapper.toEntity(reviewDto);
        review.setUser(user);
        review.setProduct(product);

        productService.updateNewReviewRating(product, review.getRating());

        return mapper.toDto(reviewRepository.save(review));
    }

    @Override
    public Page<ReviewDto> getReviewsByCriterion(ReviewGetRequest reviewGetRequest, Pageable pageable) {
        Specification<Review> spec = Specification.where(null);

        if (reviewGetRequest.getProductId() != null) {
            spec = spec.and(belongProductId(reviewGetRequest.getProductId()));
        }

        if (reviewGetRequest.getUserId() != null) {
            spec = spec.and(belongUserId(reviewGetRequest.getUserId()));
        }

        if (reviewGetRequest.getRating() != null) {
            spec = spec.and(hasRating(reviewGetRequest.getRating()));
        }

        var reviews = reviewRepository.findAll(spec, pageable)
                                        .map(mapper::toDto);
        return reviews;
    }
}
