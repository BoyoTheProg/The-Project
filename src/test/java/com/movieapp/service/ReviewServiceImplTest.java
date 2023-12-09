package com.movieapp.service;

import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.Review;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.repo.MovieRepository;
import com.movieapp.repo.ReviewRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private UserService userService;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testEditReview() {
        // Mock data
        Long reviewId = 1L;
        Review editedReview = new Review();
        editedReview.setReview("Edited review content");

        // Mock repository behavior
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(new Review()));

        // Call the method
        reviewService.editReview(reviewId, editedReview);

        // Verify that the review is updated
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testGetMovieById() {
        // Mock data
        Long movieId = 1L;
        Movie mockMovie = new Movie();
        mockMovie.setId(movieId);
        mockMovie.setTitle("Mock Movie");

        // Mock repository behavior
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(mockMovie));

        // Call the method
        Movie result = movieService.getMovieById(movieId);

        // Verify that the correct movie is returned
        assertNotNull(result);
        assertEquals(movieId, result.getId());
        assertEquals("Mock Movie", result.getTitle());

        // Verify that the repository method was called once with the correct ID
        verify(movieRepository, times(1)).findById(movieId);
    }

}
