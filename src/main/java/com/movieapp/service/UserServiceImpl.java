package com.movieapp.service;


import com.movieapp.model.dto.user.UserLoginBindingDto;
import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.User;
import com.movieapp.repo.UserRepository;
import com.movieapp.service.impl.LoggedUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final LoggedUser loggedUser;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
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

        User user = new User();
        user.setUsername(userRegisterBindingDto.getUsername());
        user.setEmail(userRegisterBindingDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterBindingDto.getPassword()));

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean login(UserLoginBindingDto userLoginBindingDto) {
        String username = userLoginBindingDto.getUsername();
        User user = userRepository.findByUsername(username);

        if (user != null && passwordEncoder.matches(userLoginBindingDto.getPassword(), user.getPassword())){
            loggedUser.login(username);

            return true;
        }

        return false;
    }

    @Override
    public void logout() {
        this.loggedUser.logout();
    }
}
