package com.movieapp.model.entity;

import com.movieapp.model.enums.SubscriptionType;
import jakarta.persistence.*;



@Entity
@Table(name = "plans")
public class Plan extends BaseEntity{
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionType name;
    @Column(nullable = false)
    private double price;


    public SubscriptionType getName() {
        return name;
    }

    public void setName(SubscriptionType name) {
        this.name = name;
        switch (name){
            case PREMIUM -> setPrice(14.99);
            case STANDARD -> setPrice(9.99);
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    // getters and setters
}