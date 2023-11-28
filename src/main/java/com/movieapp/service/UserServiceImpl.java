package com.movieapp.service;


import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.Plan;
import com.movieapp.model.entity.Subscription;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.repo.PlanRepository;
import com.movieapp.repo.SubscriptionRepository;
import com.movieapp.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final PlanRepository planRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository, PlanRepository planRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(UserRegisterBindingDto userRegisterBindingDto) {
        if (!userRegisterBindingDto.getPassword().equals(userRegisterBindingDto.getConfirmPassword())){
            return false;
        }

        boolean existsByUsernameOrEmail = userRepository.existsByUsernameOrEmail(
                userRegisterBindingDto.getUsername(), userRegisterBindingDto.getEmail()
        );
        if (existsByUsernameOrEmail){
            return false;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegisterBindingDto.getUsername());
        userEntity.setEmail(userRegisterBindingDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRegisterBindingDto.getPassword()));


        // Save the user first to obtain the user ID
        userEntity = userRepository.save(userEntity);

        // Retrieve the selected plan
//        Plan plan = planRepository.findByName(userRegisterBindingDto.getPlan().getName());

        // Create a subscription
        Subscription subscription = new Subscription();
//        subscription.setPlan(plan);
        subscription.setUser(userEntity);
        subscription.setCreatedOn(LocalDate.now());
        subscription.setValidTill(LocalDate.now().plusMonths(1));

        // Save the subscription
        subscriptionRepository.save(subscription);


        return true;
    }

}
