package com.movieapp.service;

import com.movieapp.model.entity.Review;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewService {
    @Query(value = "SELECT * FROM reviews WHERE movie_id = :movieId", nativeQuery = true)
    List<Review> getReviewsByMovieId(Long movieId);

    Review saveReview(Long movieId, String review, Double newRating);

    Review getReviewById(Long reviewId);

    void deleteReview(Long reviewId);

    void editReview(Long reviewId, Review editedReview);
}

