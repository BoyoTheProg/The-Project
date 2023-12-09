package com.movieapp.service;

import com.movieapp.model.dto.user.UserRegisterBindingDto;
import com.movieapp.model.entity.*;
import com.movieapp.model.enums.SubscriptionType;
import com.movieapp.model.enums.UserRoleEnum;
import com.movieapp.repo.PlanRepository;
import com.movieapp.repo.RoleRepository;
import com.movieapp.repo.SubscriptionRepository;
import com.movieapp.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PlanRepository planRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {
        // Mock data
        UserRegisterBindingDto userDto = new UserRegisterBindingDto();

        userDto.setUsername("BobbyRRR");
        userDto.setPassword("Test");
        userDto.setConfirmPassword("Test");
        userDto.setEmail("example@emp.com");
        userDto.setPlan(SubscriptionType.STANDARD);

        // Mock repository and encoder behavior
        when(userRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());
        when(roleRepository.findByRole(any())).thenReturn(Optional.of(new RoleEntity()));

        // Call the method
        boolean result = userService.register(userDto);

        // Verify that the user is saved
        assertTrue(result);
        verify(userRepository, times(2)).save(any(UserEntity.class));
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }


    @Test
    void testChangeUserRole() {
        // Mock data
        Long userId = 1L;
        UserRoleEnum selectedRole = UserRoleEnum.ADMIN;

        // Mock repository behavior
        when(roleRepository.findByRole(selectedRole)).thenReturn(Optional.of(new RoleEntity()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));

        // Call the method
        userService.changeUserRole(userId, selectedRole);

        // Verify that the user role is updated
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testDeleteUserAndSubscription() {
        // Mocking a UserEntity with a specific user ID
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        // Mocking the UserRepository to return the UserEntity when findById is called
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        // Call the method to be tested
        userService.deleteUserAndSubscription(1L);

        // Verify that userRepository.delete was called with the correct userEntity
        verify(userRepository).delete(userEntity);
    }

    @Test
    void testGetUserById() {
        // Mocking a UserEntity with a specific user ID
        UserEntity expectedUser = new UserEntity();
        expectedUser.setId(1L);

        // Mocking the UserRepository to return the UserEntity when findById is called
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));

        // Call the method to be tested
        UserEntity actualUser = userService.getUserById(1L);

        // Verify that userRepository.findById was called with the correct user ID
        Mockito.verify(userRepository).findById(1L);

        // Assert that the returned user is the expected user
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testGetCurrentUserWhenNotAuthenticated() {
        // Set up the SecurityContextHolder with null authentication
        SecurityContextHolder.getContext().setAuthentication(null);

        // Call the method under test
        UserEntity currentUser = userService.getCurrentUser();

        // Assertions
        assertNull(currentUser);
    }
}
