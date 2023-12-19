package com.movieapp.service;


import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.Subscription;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.model.enums.UserRoleEnum;

import java.util.List;

public interface UserService {

    boolean register(UserRegisterBindingDto userRegisterBindingDto) throws Exception;

    UserEntity getCurrentUser();

    UserEntity getUserById(Long id);

    void deleteUserAndSubscription(Long userId);

    List<UserRoleEnum> getAllRoles();

    void changeUserRole(Long userId, UserRoleEnum selectedRole);

    Subscription getUserSubscriptionByUserId(Long userId);

    Long getUserIdByUsername(String username);

    UserEntity getUserByUsername(String username);
}
