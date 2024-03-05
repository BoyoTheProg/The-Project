package com.movieapp.controller;


import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.service.MovieService;
import com.movieapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {


    private final MovieService movieService;
    private final UserService userService;

    public HomeController(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView index(){

        return new ModelAndView("index");
    }

    @GetMapping("/home")
    public String home(Model model){

        MovieHomeDto movieHomeDto = movieService.getHomeViewData();
        model.addAttribute("movies", movieHomeDto.getAvailableMovies());
        model.addAttribute("lastWatchedMovies", movieHomeDto.getLastWatchedMovies());
        model.addAttribute("recommendedMovies", movieHomeDto.getRecommendedMovies());
        model.addAttribute("userId", userService.getCurrentUser().getId());

        return "home";
    }
}
