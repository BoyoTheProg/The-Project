package com.movieapp.repo;

import com.movieapp.model.entity.Subscription;
import com.movieapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM subscriptions")
    List<Subscription> getAllAvailable();

    Subscription findByUserEntity(Optional<UserEntity> userEntity);
}
