package com.movieapp.model.dto.subscription;

import com.movieapp.model.dto.plan.PlanDto;
import com.movieapp.model.dto.user.UserDto;
import com.movieapp.model.entity.Subscription;

import java.time.LocalDate;

public class SubscriptionDto {

    private UserDto user;
    private PlanDto plan;
    private LocalDate createdOn;
    private LocalDate validTill;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public PlanDto getPlan() {
        return plan;
    }

    public void setPlan(PlanDto plan) {
        this.plan = plan;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getValidTill() {
        return validTill;
    }

    public void setValidTill(LocalDate validTill) {
        this.validTill = validTill;
    }

    public static SubscriptionDto createFromSubscription(Subscription subscription) {
        if (subscription == null) {
            return new SubscriptionDto(); // or handle as needed
        }

        SubscriptionDto dto = new SubscriptionDto();
        dto.setUser(UserDto.createFromUser(subscription.getUser()));
        dto.setPlan(PlanDto.createFromPlan(subscription.getPlan()));
        dto.setCreatedOn(subscription.getCreatedOn());
        dto.setValidTill(subscription.getValidTill());
        return dto;
    }



}
