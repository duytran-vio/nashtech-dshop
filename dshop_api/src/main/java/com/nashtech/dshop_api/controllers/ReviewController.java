package com.nashtech.dshop_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.dshop_api.dto.requests.ReviewGetRequest;
import com.nashtech.dshop_api.dto.responses.ReviewDto;
import com.nashtech.dshop_api.services.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController{
    
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Object> createReview(@Valid @RequestBody ReviewDto reviewDto){
        var review = reviewService.createReview(reviewDto);
        return ResponseEntity.ok().body(review);
    }

    @GetMapping
    public ResponseEntity<Object> getReviewsByCriterion(@Valid ReviewGetRequest reviewGetRequest, Pageable pageable){
        var reviews = reviewService.getReviewsByCriterion(reviewGetRequest, pageable);
        return ResponseEntity.ok().body(reviews);
    }
}
