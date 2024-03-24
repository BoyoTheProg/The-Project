package com.movieapp.controller;

import com.movieapp.model.dto.movie.MovieDTO;
import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.model.entity.Movie;
import com.movieapp.model.enums.GenreType;
import com.movieapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GenreController {

    @Autowired
    private MovieService movieService;

    @ModelAttribute("genres")
    public GenreType[] genres() {
        return GenreType.values();
    }

    @GetMapping("/genres/{genre}")
    public String genreDetails(@PathVariable GenreType genre, Model model) {
        // Add logic to fetch movies by genre and populate the model
        MovieHomeDto movies = movieService.getGenreViewData(genre);
        model.addAttribute("movies", movies.getAvailableMovies());
        model.addAttribute("mostWatchedMovie", movieService.getMostWatchedMovieByGenre(genre.toString()));

        // Return the name of the Thymeleaf template for displaying genre details
        return "genre-details"; // Change this to the actual template name
    }




    @GetMapping("/genres")
    public String genresView(Model model) {
        GenreType[] allGenres = GenreType.values();
        List<String> posterUrls = new ArrayList<>();

        for (GenreType genre : allGenres) {
            String posterUrl = movieService.getRandomMoviePoster(genre);
            posterUrls.add(posterUrl);
        }

        model.addAttribute("posterUrls", posterUrls);
        model.addAttribute("genres", allGenres);
        return "genres";
    }


}
