package com.movieapp.service;


import com.movieapp.model.dto.user.UserLoginBindingDto;
import com.movieapp.model.dto.user.UserRegisterBindingDto;

public interface UserService {

    boolean register(UserRegisterBindingDto userRegisterBindingDto);

    boolean login(UserLoginBindingDto userLoginBindingDto);

    void logout();
}
