package com.movieapp.controller;

import com.movieapp.model.dto.movie.MovieAddBindingDto;
import com.movieapp.model.dto.movie.MovieDTO;
import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.Subscription;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.model.enums.SubscriptionType;
import com.movieapp.service.MovieService;
import com.movieapp.service.SubscriptionService;
import com.movieapp.service.UserDetailsServiceImpl;
import com.movieapp.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;

    @Autowired
    private UserDetailsService userDetailsService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {

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
    public String register(@ModelAttribute("userRegistrationDTO") UserRegisterBindingDto userRegistrationDTO) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userRegistrationDTO") @Valid UserRegisterBindingDto userRegistrationDTO,
                           BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            // Return the registration form with validation errors
            model.addAttribute("userRegistrationDTO", userRegistrationDTO); // Add the model attribute back to the model
            return "register";
        }
            boolean registrationSuccess = userService.register(userRegistrationDTO);

            if (registrationSuccess) {
                // Authenticate the user after registration
                authenticateUser(userRegistrationDTO.getUsername(), userRegistrationDTO.getPassword());
                // Redirect the user based on their subscription plan
                if (userRegistrationDTO.getPlan() == SubscriptionType.PREMIUM) {
                    return "redirect:/premium-register";
                } else {
                    return "redirect:/";
                }
            } else {
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


    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        try {
            byte[] profilePic = userService.getUserProfilePic();

            UserEntity user = userService.getCurrentUser();

            Subscription subscription = user.getSubscription();

            MovieDTO movie = movieService.getMostWatchedMovieByUser();

            if (profilePic != null) {
                model.addAttribute("userId", userService.getCurrentUser().getId());
                model.addAttribute("profilePic", Base64.getEncoder().encodeToString(profilePic));
            } else {
                // If the profile picture doesn't exist, show a placeholder or default image
                model.addAttribute("profilePic", "/images/transformer.jpg");
                model.addAttribute("userId", userService.getCurrentUser().getId());
            }

            model.addAttribute("profile", user);
            model.addAttribute("promoCode", subscription.getPromoCode());
            model.addAttribute("subscription", subscription);
            model.addAttribute("movie", movie);
        } catch (IOException e) {
            // Handle the exception appropriately (e.g., log it or show a default image)
            e.printStackTrace();
        }

        // Add other user-related data to the model as needed
        // ...

        return "userProfilePage";
    }

    @GetMapping("/profile/{userId}/upload")
    public String showProfilePicUploadForm( Model model) throws IOException {
        // Check if the user already has a profile picture
        byte[] profilePic = userService.getUserProfilePic();

        if (profilePic != null) {
            model.addAttribute("profilePic", Base64.getEncoder().encodeToString(profilePic));
        } else {
            // If the profile picture doesn't exist, show a placeholder or default image
            model.addAttribute("profilePic", "/images/transformer.jpg");
        }
        // If the user doesn't have a profile picture, show the upload form
        model.addAttribute("userId", userService.getCurrentUser().getId());
        return "profilePicUploadForm";
    }


    @PostMapping("/profile/{userId}/upload")
    public String handleProfilePicUpload(@PathVariable Long userId,
                                         @RequestParam("profilePicFile") MultipartFile file) {
        // Handle the profile picture upload
        Long userId1 = userService.getCurrentUser().getId();
        try {
            userService.setUserProfilePic(file);
        } catch (IOException e) {
            // Handle the exception appropriately (e.g., show an error message)
            e.printStackTrace();
        }

        // Redirect to the user profile page
        return "redirect:/profile/" + userId1;
    }

}
