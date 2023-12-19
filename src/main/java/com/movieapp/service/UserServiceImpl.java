package com.movieapp.service;


import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.Plan;
import com.movieapp.model.entity.RoleEntity;
import com.movieapp.model.entity.Subscription;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.model.enums.SubscriptionType;
import com.movieapp.model.enums.UserRoleEnum;
import com.movieapp.repo.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.movieapp.model.PromoCodeGenerator.generateRandomPromoCode;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final RoleRepository roleRepository;

    private final PlanRepository planRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository, RoleRepository roleRepository, PlanRepository planRepository, ReviewRepository reviewRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.roleRepository = roleRepository;
        this.planRepository = planRepository;
        this.reviewRepository = reviewRepository;
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

        //Retrieve the selected plan
        Plan plan = planRepository.findByName(userRegisterBindingDto.getPlan());


        // Create a subscription
        Subscription subscription = new Subscription();
        subscription.setPlan(plan);
        subscription.setUser(userEntity);
        subscription.setCreatedOn(LocalDate.now());
        subscription.setValidTill(LocalDate.now().plusMonths(1));

        if (userRegisterBindingDto.getPlan().equals(SubscriptionType.PREMIUM)){
            String promoCode = generateRandomPromoCode();
            subscription.setPromoCode(promoCode);
        }

        // Save the subscription
        subscriptionRepository.save(subscription);

        // Assign the default role USER to the user
        UserRoleEnum defaultRole = UserRoleEnum.USER;
        RoleEntity role = roleRepository.findByRole(defaultRole)
                .orElseThrow(() -> new IllegalArgumentException("Default role not found"));

        // Set the roles for the user
        List<RoleEntity> userRoles = Collections.singletonList(role);
        userEntity.setRoles(userRoles);

        // Update the user entity with the assigned role
        userRepository.save(userEntity);

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

    @Override
    public UserEntity getUserById(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    @Override
    public void deleteUserAndSubscription(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.getRoles().clear();
            userRepository.delete(user);
        }
    }

    @Override
    public List<UserRoleEnum> getAllRoles() {
        return Arrays.asList(UserRoleEnum.values());
    }

    @Override
    public void changeUserRole(Long userId, UserRoleEnum selectedRole) {
        RoleEntity role = roleRepository.findByRole(selectedRole)
                .orElseThrow(() -> new IllegalArgumentException("Selected role not found"));

        UserEntity user = userRepository.findById(userId).orElse(null);

        List<RoleEntity> userRoles = Collections.singletonList(role);

        if (user != null) {
            user.setRoles(userRoles);
            userRepository.save(user);
        }
    }

    @Override
    public Subscription getUserSubscriptionByUserId(Long userId) {
        // Assume subscription repository method to get the user's subscription by user ID
        return subscriptionRepository.findByUserEntity(userRepository.findById(userId));
    }

    @Override
    public Long getUserIdByUsername(String username) {
        // Assume user repository method to get the user's ID by username
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user != null ? user.get().getId() : null;
    }

    @Override
    public UserEntity getUserByUsername(String username) {

        Optional<UserEntity> user = userRepository.findByUsername(username);

        return user != null ? user.get() : null;
    }
}
