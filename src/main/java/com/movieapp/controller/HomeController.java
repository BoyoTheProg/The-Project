package com.movieapp.controller;


import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.service.MovieService;
import com.movieapp.service.impl.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {


    private final LoggedUser loggedUser;

    private final MovieService movieService;

    public HomeController(LoggedUser loggedUser, MovieService movieService) {
        this.loggedUser = loggedUser;
        this.movieService = movieService;
    }

    @GetMapping("/")
    public ModelAndView index(){
        if (loggedUser.isLogged()){
            return new ModelAndView("redirect:/home");
        }

        return new ModelAndView("index");
    }

    @GetMapping("/home")
    public ModelAndView home(){
//        if (!loggedUser.isLogged()){
//            return new ModelAndView("redirect:/");
//        }

//        List<WordDTO> allGermanWords = wordService.getHomeViewData().getGermanWords();
//        WordHomeDto wordHomeDto = wordService.getHomeViewData();
//
//        return new ModelAndView("home", "words", wordHomeDto);
        MovieHomeDto movieHomeDto = movieService.getHomeViewData();

        return new ModelAndView("home1", "movies", movieHomeDto);
//        return new ModelAndView("home");
    }
}
