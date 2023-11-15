package com.movieapp.repo;

import com.movieapp.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);

    @Query(nativeQuery = true, value = "SELECT * FROM movies")
    List<Movie> getAllAvailable();

    Movie getById(Long id);
}
