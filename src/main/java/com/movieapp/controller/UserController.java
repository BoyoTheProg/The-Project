package com.movieapp.controller;

import com.movieapp.model.dto.user.UserLoginBindingDto;
import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.service.UserService;
import com.movieapp.service.impl.LoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final LoggedUser loggedUser;
    private final UserService userService;

    public UserController(LoggedUser loggedUser, UserService userService) {
        this.loggedUser = loggedUser;
        this.userService = userService;
    }


    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute("UserLoginBindingDto") UserLoginBindingDto userLoginBindingDto){
        if (loggedUser.isLogged()){
            return new ModelAndView("redirect:/home");
        }

        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("UserLoginBindingDto") @Valid UserLoginBindingDto userLoginBindingDto
            , BindingResult bindingResult){

        if (loggedUser.isLogged()){
            return new ModelAndView("redirect:/home");
        }

        if (bindingResult.hasErrors()){
            return new ModelAndView("login");
        }

        boolean hasSuccessfulLogin = userService.login(userLoginBindingDto);

        if (!hasSuccessfulLogin){
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("hasLoginError", true);

            return modelAndView;
        }

        return new ModelAndView("redirect:/home");
    }


    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("UserRegisterBindingDto") UserRegisterBindingDto userRegisterBindingDto){
        if (loggedUser.isLogged()){
            return new ModelAndView("redirect:/home");
        }

        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("UserRegisterBindingDto") @Valid UserRegisterBindingDto userRegisterBindingDto
            , BindingResult bindingResult){
        if (loggedUser.isLogged()){
            return new ModelAndView("redirect:/home");
        }

        if (bindingResult.hasErrors()){
            return new ModelAndView("register");
        }

        boolean hasSuccessfulRegistration = userService.register(userRegisterBindingDto);

        if (!hasSuccessfulRegistration){
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("hasRegistrationError", true);

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/logout")
    public ModelAndView logout(){
        if (!loggedUser.isLogged()){
            return new ModelAndView("redirect:/home");
        }

        this.userService.logout();
        return new ModelAndView("redirect:/");
    }
}
