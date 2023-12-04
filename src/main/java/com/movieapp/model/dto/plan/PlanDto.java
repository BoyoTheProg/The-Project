package com.movieapp.model.dto.plan;

import com.movieapp.model.entity.Plan;

public class PlanDto {
    private Long id;
    private String name;
    private double price;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static PlanDto createFromPlan(Plan plan) {
        PlanDto planDto = new PlanDto();
        planDto.setId(plan.getId());
        planDto.setName(String.valueOf(plan.getName()));
        planDto.setPrice(plan.getPrice());
        return planDto;
    }
}
