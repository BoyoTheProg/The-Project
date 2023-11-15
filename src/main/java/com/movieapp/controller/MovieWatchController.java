package com.movieapp.controller;


import com.movieapp.model.dto.movie.MovieDetailDto;
import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.model.entity.Movie;
import com.movieapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies")
public class MovieWatchController {

    @Autowired
    private MovieService movieService; // Assume you have a MovieService to fetch movie data

    @GetMapping("/{id}")
        public String getMovieById(@PathVariable Long id, Model model) {
            Movie movie = movieService.getMovieById(id);
            model.addAttribute("movie", movie);
            return "movie";
        }
}

