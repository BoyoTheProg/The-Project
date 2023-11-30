package com.movieapp.service;

import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.Review;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.repo.ReviewRepository;
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

    @Override
    public List<Review> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
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
}
