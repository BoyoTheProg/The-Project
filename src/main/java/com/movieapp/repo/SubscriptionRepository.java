package com.movieapp.repo;

import com.movieapp.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM subscriptions")
    List<Subscription> getAllAvailable();
}
