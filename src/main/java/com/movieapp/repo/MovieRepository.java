package com.movieapp.repo;

import com.movieapp.model.entity.Movie;
import com.movieapp.model.enums.GenreType;
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

    @Query(value = "SELECT * FROM movie_app.movies WHERE genre = :genreName", nativeQuery = true)
    List<Movie> getMoviesByGenre(@Param("genreName") String genreName);

}
