package com.movieapp.controller;

import com.movieapp.model.entity.Movie;
import com.movieapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MovieWatchController {

    @Autowired
    private MovieService movieService; // Assume you have a MovieService to fetch movie data

    @GetMapping("/movies/{id}")
        public String getMovieById(@PathVariable Long id, Model model) {
            Movie movie = movieService.getMovieById(id);
            model.addAttribute("movie", movie);
            return "movie";
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

