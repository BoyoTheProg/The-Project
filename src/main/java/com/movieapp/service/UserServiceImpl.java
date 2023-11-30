package com.movieapp.service;


import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.Subscription;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.repo.PlanRepository;
import com.movieapp.repo.SubscriptionRepository;
import com.movieapp.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                // Spring Security User object, extract the username
                String username = ((User) principal).getUsername();

                // Assuming you have a method to get the user by username returning Optional<UserEntity>
                Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);

                // Return the UserEntity if present in the Optional
                return userEntityOptional.orElse(null);
            }
        }

        return null;
    }
}
