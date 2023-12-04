package com.movieapp.controller;

import com.movieapp.model.dto.subscription.SubscriptionDto;
import com.movieapp.model.entity.Subscription;
import com.movieapp.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    public String showSubscriptionPage(Model model) {

        List<SubscriptionDto> availableSubscriptions = subscriptionService.getAllSubscriptions();

        model.addAttribute("subscriptions", availableSubscriptions);

        return "subscription";
    }
}

