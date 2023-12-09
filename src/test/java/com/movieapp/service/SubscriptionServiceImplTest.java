package com.movieapp.service;

import com.movieapp.model.dto.plan.PlanDto;
import com.movieapp.model.dto.role.RoleDto;
import com.movieapp.model.dto.subscription.SubscriptionDto;
import com.movieapp.model.dto.user.UserDto;
import com.movieapp.model.entity.Plan;
import com.movieapp.model.entity.Subscription;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.model.enums.SubscriptionType;
import com.movieapp.model.enums.UserRoleEnum;
import com.movieapp.repo.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SubscriptionServiceImplUnitTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    void testGetAllSubscriptions() {
        UserEntity userDto = new UserEntity();
        userDto.setId(1L);
        userDto.setUsername("exampleUser");
        userDto.setEmail("user@example.com");

        Plan planDto = new Plan();
        planDto.setId(1L);
        planDto.setName(SubscriptionType.PREMIUM);
        planDto.setPrice(19.99);


        // Mock data
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setUser(userDto);
        subscription.setPlan(planDto);
        subscription.setCreatedOn(LocalDate.now());
        subscription.setValidTill(LocalDate.now().plusMonths(1));



        Mockito.when(subscriptionRepository.getAllAvailable()).thenReturn(Collections.singletonList(subscription));

        // Call the method
        List<SubscriptionDto> subscriptionDtos = subscriptionService.getAllSubscriptions();

        // Assertions
        assertEquals(1, subscriptionDtos.size());
        assertEquals(subscription.getUser().getId(), subscriptionDtos.get(0).getUser().getId());
        assertEquals(subscription.getPlan().getId(), subscriptionDtos.get(0).getPlan().getId());
        assertEquals(subscription.getCreatedOn(), subscriptionDtos.get(0).getCreatedOn());
        assertEquals(subscription.getValidTill(), subscriptionDtos.get(0).getValidTill());
        // Add more assertions based on your SubscriptionDto structure
    }
}


