package com.movieapp.service;

import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.Review;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.repo.ReviewRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieService movieService;
    private final UserService userService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieService movieService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.movieService = movieService;
        this.userService = userService;
    }

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Review> getReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewRepository.getReviewsByMovieId(movieId);
        fetchUserEntities(reviews);
        return reviews;
    }

    private void fetchUserEntities(List<Review> reviews) {
        for (Review review : reviews) {
            if (!Hibernate.isInitialized(review.getUserEntity())) {
                entityManager.detach(review); // Detach the entity to avoid automatic initialization
                review.setUser(userService.getUserById(review.getUserEntity().getId()));
            }
        }
    }

    @Override
    public Review saveReview(Long movieId, String reviewContent) {
        Movie movie = movieService.getMovieById(movieId);
        UserEntity user = userService.getCurrentUser();

        if (movie != null && user != null) {
            Review review = new Review();
            review.setMovie(movie);
            review.setUser(user);
            review.setReview(reviewContent);
            return reviewRepository.save(review);
        }
        return null;
    }
    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }
    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
    @Override
    public void editReview(Long reviewId, Review editedReview) {
        Review existingReview = reviewRepository.findById(reviewId).orElseThrow(() -> new NullPointerException("Review not found"));

        existingReview.setReview(editedReview.getReview());

        reviewRepository.save(existingReview);
    }
}
