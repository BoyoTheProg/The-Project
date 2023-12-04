package com.movieapp.controller;

import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


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




//    @PostMapping("/register")
//    public ModelAndView register(@ModelAttribute("UserRegisterBindingDto") @Valid UserRegisterBindingDto userRegisterBindingDto
//            , BindingResult bindingResult){
//
//
//        if (bindingResult.hasErrors()){
//            return new ModelAndView("register");
//        }
//
//        return new ModelAndView("redirect:/login");
//    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserRegisterBindingDto userRegistrationDTO) throws Exception {

        userService.register(userRegistrationDTO);

        return "redirect:/";
    }
}
