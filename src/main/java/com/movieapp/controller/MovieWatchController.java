package com.movieapp.controller;

import com.movieapp.model.dto.movie.MovieDTO;
import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.Review;
import com.movieapp.service.MovieService;
import com.movieapp.service.ReviewService;
import com.movieapp.service.UserMovieInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.List;

@Controller
public class MovieWatchController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private MovieService movieService; // Assume you have a MovieService to fetch movie data
    @Autowired
    private UserMovieInteractionService userMovieInteractionService;


    @GetMapping("/movies/{id}")
        public String getMovieById(@PathVariable Long id, Model model) {
            Movie m = movieService.getMovieById(id);
            MovieDTO movie = MovieDTO.createFromMovie(m);
            List<Review> reviews = reviewService.getReviewsByMovieId(id);
            String profilePic = "";
            for (Review review: reviews) {
                double rating = review.getRating();
                if (rating > 0 && rating < 4){
                    profilePic = "https://images.emojiterra.com/google/noto-emoji/unicode-15/animated/1f620.gif";
                } else if (rating > 3 && rating < 6) {
                    profilePic = "https://assets-v2.lottiefiles.com/a/45cb6bd0-116b-11ee-ab1e-c37d9971e10f/GbrbMx3XE2.gif";
                } else if (rating > 5 && rating < 9) {
                    profilePic = "https://cdn0.iconfinder.com/data/icons/remoji-soft-1/512/emoji-cool-smile-sunglasses.png";
                } else {
                    profilePic = "https://cdn.pixabay.com/animation/2022/10/27/12/57/12-57-22-874_512.gif";
                }
                review.setEmoji(profilePic);
            }
            model.addAttribute("reviews", reviews);
            model.addAttribute("movie", movie);
            return "movie";
        }

    @PostMapping("/movies/{id}/interaction")
    public String saveUserMovieInteraction(@PathVariable Long id, @RequestParam(name = "watched", defaultValue = "true") boolean watched) {
        userMovieInteractionService.saveUserMovieInteraction(id, watched);
        return "redirect:/movies/" + id;
    }

    @PostMapping("/movies/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
//        if (!loggedUser.isLogged()){
//            return new ModelAndView("redirect:/");
//        }

        movieService.remove(id);

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/movies/edit/{id}")
    public String editMovieForm(@PathVariable Long id, Model model) {
        // Retrieve the movie by ID from the database
        Movie movie = movieService.getMovieById(id);

        // Populate the model with the movie data
        model.addAttribute("movie", movie);

        // Return the name of the edit movie form template (e.g., edit-movie.html)
        return "edit-movie";
    }

    // Post mapping to handle form submission when editing a movie
    @PutMapping("/movies/edit/{id}")
    public String editMovie(@PathVariable Long id, @ModelAttribute Movie editedMovie) {
        // Logic to update the movie in the database
        movieService.editMovie(id, editedMovie);

        // Redirect to the movie details page
        return "redirect:/movies/" + id;
    }
}

