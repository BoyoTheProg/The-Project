package com.movieapp.controller;

import com.movieapp.model.dto.movie.MovieAddBindingDto;
import com.movieapp.service.MovieService;
import com.movieapp.service.impl.LoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class MovieController {
    private final MovieService movieService;

    private final LoggedUser loggedUser;

    public MovieController(MovieService movieService, LoggedUser loggedUser) {
        this.movieService = movieService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/movies/add")
    public ModelAndView add(@ModelAttribute("movieAddBindingDto") MovieAddBindingDto movieAddBindingDto){


        return new ModelAndView("movie-add");
    }

    @PostMapping("/movies/add")
    public ModelAndView add(
            @ModelAttribute("movieAddBindingDto") @Valid MovieAddBindingDto movieAddBindingDto
            ,BindingResult bindingResult){

        if (!loggedUser.isLogged()){
            return new ModelAndView("redirect:/");
        }

        if (bindingResult.hasErrors()){
            return new ModelAndView("movie-add");
        }

        movieService.add(movieAddBindingDto);

        return new ModelAndView("redirect:/home");
    }

}
