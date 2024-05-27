package com.nashtech.dshop_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nashtech.dshop_api.dto.requests.ReviewGetRequest;
import com.nashtech.dshop_api.dto.responses.ReviewDto;

public interface ReviewService {
    public ReviewDto createReview(ReviewDto reviewDto);
    public Page<ReviewDto> getReviewsByCriterion(ReviewGetRequest reviewGetRequest, Pageable pageable);
}
