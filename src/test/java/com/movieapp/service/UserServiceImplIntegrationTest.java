package com.movieapp.service;

import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.RoleEntity;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.model.enums.SubscriptionType;
import com.movieapp.repo.PlanRepository;
import com.movieapp.repo.RoleRepository;
import com.movieapp.repo.SubscriptionRepository;
import com.movieapp.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplIntegrationTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PlanRepository planRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterUserIntegration() throws Exception {
        // Mock data
        UserRegisterBindingDto userDto = new UserRegisterBindingDto();

        userDto.setUsername("BobbyRRR");
        userDto.setPassword("Test");
        userDto.setConfirmPassword("Test");
        userDto.setEmail("example@emp.com");
        userDto.setPlan(SubscriptionType.STANDARD);

        // Mock repository and encoder behavior
        when(userRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(new UserEntity());
        when(roleRepository.findByRole(any())).thenReturn(java.util.Optional.of(new RoleEntity()));

        // Call the method
        boolean result = userService.register(userDto);

        // Verify that the user is saved
        assertTrue(result);
    }

    // Add more integration tests for other methods...
}