package com.movieapp.repo;

import com.movieapp.model.entity.UserMovieInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMovieInteractionRepository extends JpaRepository<UserMovieInteraction, Long> {
    @Query(value = "SELECT * FROM movie_app.watch_history WHERE user_id = :userId", nativeQuery = true)
    List<UserMovieInteraction> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM watch_history WHERE (user_id, movie_id, timestamp)" +
            " IN (SELECT user_id, movie_id, MAX(timestamp) " +
            " AS latest_timestamp FROM watch_history WHERE user_id = :userId " +
            " GROUP BY user_id, movie_id) ORDER BY timestamp DESC LIMIT 5",
            nativeQuery = true)
    List<UserMovieInteraction> findLastFiveInteractions(@Param("userId") Long userId);
//    List<UserMovieInteraction> findTopNByUserOrderByTimestampDesc(Long userId, int count);

}

