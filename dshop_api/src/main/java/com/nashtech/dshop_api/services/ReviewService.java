package com.nashtech.dshop_api.services;

import java.util.List;

import com.nashtech.dshop_api.dto.requests.ReviewGetRequest;
import com.nashtech.dshop_api.dto.responses.ReviewDto;

public interface ReviewService {
    public ReviewDto createReview(ReviewDto reviewDto);
    public List<ReviewDto> getReviewsByCriterion(ReviewGetRequest reviewGetRequest);
}
