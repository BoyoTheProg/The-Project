package com.movieapp.repo;

import com.movieapp.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT * FROM reviews WHERE movie_id = :movieId", nativeQuery = true)
    List<Review> getReviewsByMovieId(Long movieId);

    Review getReviewById(Long reviewId);
}
