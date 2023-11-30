package com.movieapp.service;

import com.movieapp.model.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByMovieId(Long movieId);

    Review saveReview(Long movieId, String review);
}

