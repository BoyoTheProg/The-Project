package com.movieapp.controller;

import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.Review;
import com.movieapp.service.MovieService;
import com.movieapp.service.ReviewService;
import com.movieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class ReviewController {

    private final ReviewService reviewService;
    private final MovieService movieService;

    @Autowired
    public ReviewController(ReviewService reviewService, MovieService movieService) {
        this.reviewService = reviewService;
        this.movieService = movieService;
    }

    @GetMapping("/reviews/{movieId}")
    public String showReviews(@PathVariable Long movieId, Model model) {
        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
        model.addAttribute("reviews", reviews);
        return "reviews/list"; // Thymeleaf template name
    }

    @GetMapping("/reviews/add/{movieId}")
    public String showReviewForm(@PathVariable Long movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);

        model.addAttribute("movie", movie);
        model.addAttribute("review", new Review());
        model.addAttribute("movieId", movieId);
        return "add-review"; // Thymeleaf template name
    }

    @PostMapping("/reviews/add/{movieId}")
    public String saveReview(@PathVariable Long movieId, @RequestParam String review) {
        reviewService.saveReview(movieId, review);
        return "redirect:/movies/{movieId}";
    }
}