package com.movieapp.controller;

import com.movieapp.model.dto.subscription.SubscriptionDto;
import com.movieapp.model.enums.UserRoleEnum;
import com.movieapp.service.SubscriptionService;
import com.movieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping("/subscriptions")
    public String showSubscriptionPage(Model model) {

        List<SubscriptionDto> availableSubscriptions = subscriptionService.getAllSubscriptions();

        model.addAttribute("subscriptions", availableSubscriptions);

        List<UserRoleEnum> allRoles = userService.getAllRoles();

        model.addAttribute("subscriptions", availableSubscriptions);
        model.addAttribute("allRoles", allRoles);

        return "subscription";
    }

    @PostMapping("/deleteUserAndSubscription/{userId}")
    public String deleteUserAndSubscription(@PathVariable Long userId) {
        userService.deleteUserAndSubscription(userId);
        return "redirect:/subscriptions";
    }

    @PostMapping("/changeUserRole/{userId}")
    public String changeUserRole(@PathVariable Long userId, @RequestParam("selectedRole") UserRoleEnum selectedRole) {
        userService.changeUserRole(userId, selectedRole);
        return "redirect:/subscriptions";
    }
}

