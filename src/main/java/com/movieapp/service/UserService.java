package com.movieapp.service;


import com.movieapp.model.dto.user.UserRegisterBindingDto;

public interface UserService {

    boolean register(UserRegisterBindingDto userRegisterBindingDto) throws Exception;


}
