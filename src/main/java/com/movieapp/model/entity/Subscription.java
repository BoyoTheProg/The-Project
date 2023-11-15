package com.movieapp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
public class Subscription extends BaseEntity{
    @Column(nullable = false, name = "created_on")
    @PastOrPresent
    private LocalDate createdOn;
    @Column(nullable = false, name = "valid_till")
    @Future
    private LocalDate validTill;
    @ManyToOne
    private Plan plan;
    @OneToOne
    private User user;

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

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    // getters and setters
}

