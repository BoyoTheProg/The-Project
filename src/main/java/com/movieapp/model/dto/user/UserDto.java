package com.movieapp.model.dto.user;

import com.movieapp.model.dto.role.RoleDto;
import com.movieapp.model.dto.subscription.SubscriptionDto;
import com.movieapp.model.entity.UserEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private SubscriptionDto subscription;
    private List<RoleDto> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SubscriptionDto getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionDto subscription) {
        this.subscription = subscription;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    private static Set<Long> processedUserIds = new HashSet<>();

    public static UserDto createFromUser(UserEntity user) {
        if (processedUserIds.contains(user.getId())) {
            // Entity already processed, return an empty DTO or handle as needed
            return new UserDto();
        }

        processedUserIds.add(user.getId());

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setSubscription(SubscriptionDto.createFromSubscription(user.getSubscription()));
        userDto.setRoles(RoleDto.createFromRoles(user.getRoles()));

        processedUserIds.remove(user.getId());  // Remove the processed entity

        return userDto;
    }
}
