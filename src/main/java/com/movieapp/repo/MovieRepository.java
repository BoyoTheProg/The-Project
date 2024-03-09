package com.movieapp.repo;

import com.movieapp.model.entity.Movie;
import com.movieapp.model.enums.GenreType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);

    @Query(nativeQuery = true, value = "SELECT * FROM movies")
    List<Movie> getAllAvailable();

    Movie getById(Long id);

    @Query(value = "SELECT * FROM movie_app.movies ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Movie> getLatestMovies();

    @Query(value = "SELECT * FROM movie_app.movies WHERE genre = :genreName", nativeQuery = true)
    List<Movie> getMoviesByGenre(@Param("genreName") String genreName);

    @Query(value = "SELECT m.* FROM movie_app.movies m " +
            "JOIN (SELECT movie_id, MAX(timestamp) AS latest_timestamp " +
            "      FROM movie_app.watch_history " +
            "      WHERE user_id = :userId " +
            "      GROUP BY movie_id) umi ON umi.movie_id = m.id " +
            "ORDER BY umi.latest_timestamp DESC " +
            "LIMIT :count", nativeQuery = true)
    List<Movie> getLastWatchedMovies(@Param("userId") Long userId, @Param("count") int count);


}
