package com.movieapp.controller;

import com.movieapp.model.dto.movie.MovieAddBindingDto;
import com.movieapp.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class MovieAddController {
    private final MovieService movieService;


    public MovieAddController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies/add")
    public ModelAndView add(@ModelAttribute("movieAddBindingDto") MovieAddBindingDto movieAddBindingDto){


        return new ModelAndView("movie-add");
    }

    @PostMapping("/movies/add")
    public ModelAndView add(@ModelAttribute("movieAddBindingDto") @Valid MovieAddBindingDto movieAddBindingDto
            ,BindingResult bindingResult){


        if (bindingResult.hasErrors()){
            return new ModelAndView("movie-add");
        }

        movieService.add(movieAddBindingDto);

        return new ModelAndView("redirect:/home");
    }

}
