package com.movieapp.service;

import com.movieapp.model.dto.subscription.SubscriptionDto;
import com.movieapp.model.entity.Subscription;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionDto> getAllSubscriptions();
}
