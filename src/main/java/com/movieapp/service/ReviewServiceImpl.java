package com.movieapp.service;

import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.Review;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.repo.MovieRepository;
import com.movieapp.repo.ReviewRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final MovieService movieService;
    private final UserService userService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieRepository movieRepository, MovieService movieService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
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
    @Transactional
    public Review saveReview(Long movieId, String reviewContent, Double newRating) {
        Movie movie = movieService.getMovieById(movieId);
        UserEntity user = userService.getCurrentUser();

        if (movie != null && user != null) {
            Review review = new Review();
            review.setMovie(movie);
            review.setUser(user);
            review.setReview(reviewContent);
            review.setRating(newRating);

            // Add the new review to the movie's list of reviews
            List<Review> movieReviews = movie.getReviews();
            movieReviews.add(review);

            // Calculate the new average rating
            double currentAverage = movie.getRating();
            double newAverage = movie.getAverageRating(); // Default to current average if no reviews yet

            if (!movieReviews.isEmpty()) {
                double totalRating = 0.0;
                for (Review review1 : movieReviews) {
                    Hibernate.initialize(review1); // Initialize the LAZY-loaded association
                    totalRating += review1.getRating();
                }
                newAverage = totalRating / movieReviews.size();
            }

            newAverage = roundToDecimalPlaces(newAverage, 1);

            // Update movie's average rating
            movie.setRating(newAverage);

            // Save the review and update the movie
            reviewRepository.save(review);
            movieRepository.save(movie);

            // Commit the transaction before leaving the method
            return review;
        }
        return null;
    }


    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public void deleteReview(Long reviewId) {

            // Delete the review
            reviewRepository.deleteById(reviewId);
    }
    public static double roundToDecimalPlaces(double value, int decimalPlaces) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("Decimal places cannot be negative.");
        }

        double scaleFactor = Math.pow(10, decimalPlaces);
        return Math.round(value * scaleFactor) / scaleFactor;
    }

    @Override
    public void editReview(Long reviewId, Review editedReview) {
        Review existingReview = reviewRepository.findById(reviewId).orElseThrow(() -> new NullPointerException("Review not found"));

        existingReview.setReview(editedReview.getReview());

        reviewRepository.save(existingReview);
    }
}
