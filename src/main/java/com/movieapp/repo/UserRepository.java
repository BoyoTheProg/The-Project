package com.movieapp.repo;


import com.movieapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsernameOrEmail(String username, String email);

    Optional<UserEntity> getByUsername(String username);
}
