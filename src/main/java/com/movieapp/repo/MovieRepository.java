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

    @Query(value = "SELECT * FROM movie_app.movies WHERE id = (SELECT wh.movie_id " +
            "FROM movie_app.watch_history wh JOIN " +
            "(SELECT movie_id, COUNT(*) AS repetitions FROM movie_app.watch_history " +
            "GROUP BY movie_id ORDER BY repetitions DESC LIMIT 1) " +
            "AS most_repetitive_movie ON wh.movie_id = most_repetitive_movie.movie_id LIMIT 1)",
            nativeQuery = true)
    Movie findMostWatchedMovie();

    @Query(value = "SELECT m.* " +
            "FROM movie_app.movies m " +
            "JOIN ( " +
            "    SELECT wh.movie_id, COUNT(*) AS repetitions " +
            "    FROM movie_app.watch_history wh " +
            "    WHERE wh.user_id = :userId " +
            "    GROUP BY wh.movie_id " +
            "    ORDER BY repetitions DESC " +
            "    LIMIT 1 " +
            ") AS most_watched_movie ON m.id = most_watched_movie.movie_id", nativeQuery = true)
    Movie findMostWatchedMovieByUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM movie_app.movies " +
            "WHERE genre = :genre " +
            "ORDER BY (SELECT COUNT(*) FROM movie_app.watch_history WHERE movie_id = movie_app.movies.id) DESC " +
            "LIMIT 1",
            nativeQuery = true)
    Movie findMostWatchedMovieByGenre(String genre);

    @Query(value = "SELECT * FROM movie_app.movies ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Movie> getLatestMovies();

    @Query(value = "SELECT * FROM movie_app.movies ORDER BY rating DESC LIMIT 20", nativeQuery = true)
    List<Movie> getTopRatedMovies();

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
