package com.movieapp.controller;

import com.movieapp.model.dto.user.UserRegisterBindingDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @GetMapping("/login")
    public String login(){

        return ("login");
    }

    @PostMapping("/login-error")
    public String onFailure(
            @ModelAttribute("username") String username,
            Model model) {

        model.addAttribute("username", username);
        model.addAttribute("bad_credentials", "true");

        return "login";
    }




    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("UserRegisterBindingDto") UserRegisterBindingDto userRegisterBindingDto){

        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("UserRegisterBindingDto") @Valid UserRegisterBindingDto userRegisterBindingDto
            , BindingResult bindingResult){


        if (bindingResult.hasErrors()){
            return new ModelAndView("register");
        }

        return new ModelAndView("redirect:/login");
    }
}
