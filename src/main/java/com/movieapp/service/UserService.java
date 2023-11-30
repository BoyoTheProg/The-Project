package com.movieapp.service;


import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.UserEntity;

public interface UserService {

    boolean register(UserRegisterBindingDto userRegisterBindingDto) throws Exception;

    UserEntity getCurrentUser();
}
