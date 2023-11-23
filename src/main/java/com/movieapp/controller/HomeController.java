package com.movieapp.controller;


import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {


    private final MovieService movieService;

    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public ModelAndView index(){

        return new ModelAndView("index");
    }

    @GetMapping("/home")
    public String home(Model model){

        MovieHomeDto movieHomeDto = movieService.getHomeViewData();
        model.addAttribute("movies", movieHomeDto.getAvailableMovies());

        return "home";
//        return new ModelAndView("home");
    }
}
