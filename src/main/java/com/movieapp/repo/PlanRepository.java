package com.movieapp.repo;

import com.movieapp.model.entity.Plan;
import com.movieapp.model.enums.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    Optional<Plan> findById(Long plan);

    Plan findByName(SubscriptionType plan);
}
