package com.movieapp.service;

import com.movieapp.model.entity.UserMovieInteraction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMovieInteractionService {

    void saveUserMovieInteraction(Long movieId, boolean watched);

    @Query(value = "SELECT * FROM watch_history " +
            "WHERE (user_id, movie_id, timestamp) IN " +
            "(SELECT user_id, movie_id, MAX(timestamp) AS latest_timestamp " +
            "FROM watch_history " +
            "WHERE user_id = :userId " +
            "GROUP BY user_id, movie_id) " +
            "ORDER BY timestamp DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<UserMovieInteraction> findLastFiveInteractions(@Param("userId") Long userId);
}
