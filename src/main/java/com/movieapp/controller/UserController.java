package com.movieapp.controller;

import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.Subscription;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.model.enums.SubscriptionType;
import com.movieapp.service.SubscriptionService;
import com.movieapp.service.UserDetailsServiceImpl;
import com.movieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

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




    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserRegisterBindingDto userRegistrationDTO, Model model) throws Exception {
        try {
            boolean registrationSuccess = userService.register(userRegistrationDTO);

            if (registrationSuccess) {
                // Authenticate the user after registration
                authenticateUser(userRegistrationDTO.getUsername(), userRegistrationDTO.getPassword());

                if (userRegistrationDTO.getPlan() == SubscriptionType.PREMIUM) {
                    return "redirect:/premium-register";
                } else {
                    return "redirect:/";
                }
            } else {
                model.addAttribute("registration_error", "true");
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("registration_error", "true");
            return "register";
        }
    }

    private void authenticateUser(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }




    @GetMapping("/premium-register")
    public String showPremiumRegistrationPage(Model model, Principal principal) {
        if (principal != null && principal.getName() != null) {
            // Retrieve the current user's ID
            Long userId = userService.getUserIdByUsername(principal.getName());

            // Retrieve the user's subscription and promo code from the database
            Subscription subscription = userService.getUserSubscriptionByUserId(userId);

            // Add the promo code to the model
            model.addAttribute("userPromoCode", subscription != null ? subscription.getPromoCode() : null);
        } else {
            // Handle the case where principal is null or principal.getName() is null
            // You might want to redirect to a login page or handle it in another way
            return "redirect:/login";
        }

        return "premium-register";
    }

}
