package com.movieapp.service;

import com.movieapp.model.dto.subscription.SubscriptionDto;
import com.movieapp.model.entity.Subscription;
import com.movieapp.repo.SubscriptionRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }




    @Transactional(readOnly = true)
    public List<SubscriptionDto> getAllSubscriptions() {
        List<Subscription> availableSubscriptions = subscriptionRepository.getAllAvailable();

        return availableSubscriptions.stream()
                .map(SubscriptionDto::createFromSubscription)
                .collect(Collectors.toList());
    }
}
