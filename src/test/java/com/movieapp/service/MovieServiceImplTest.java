package com.movieapp.service;

import com.movieapp.model.dto.movie.MovieAddBindingDto;
import com.movieapp.model.dto.movie.MovieDTO;
import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.Review;
import com.movieapp.model.enums.CategoryType;
import com.movieapp.model.enums.GenreType;
import com.movieapp.repo.MovieRepository;
import com.movieapp.repo.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddMovie() {
        // Mock data
        MovieAddBindingDto movieDto = new MovieAddBindingDto();

        movieDto.setTitle("Tony");
        movieDto.setUrl("invalid_url");
        movieDto.setPoster("poster_url");
        movieDto.setReleaseYear(2000);
        movieDto.setRuntime(15);
        movieDto.setCast("Brad Pitt");
        movieDto.setDirector("Zack Snyder");
        movieDto.setDescription("And end");
        movieDto.setGenre(GenreType.COMEDY);
        movieDto.setCategory(CategoryType.PG_13);
        movieDto.setRating(9.5);

        // Mock repository behavior
        when(movieRepository.count()).thenReturn(0L);
        when(movieRepository.save(any())).thenReturn(new Movie());

        // Call the method
        movieService.add(movieDto);

        // Verify that save was called once
        verify(movieRepository, times(1)).save(any());
    }



    @Test
    void testRemoveMovie() {
        // Mock data
        Long movieId = 1L;

        // Call the method
        movieService.remove(movieId);

        // Verify that deleteById was called once
        verify(movieRepository, times(1)).deleteById(movieId);
    }

    @Test
    void testGetHomeViewData() {
        // Mock repository behavior
        when(movieRepository.getAllAvailable()).thenReturn(new ArrayList<>());

        // Call the method
        MovieHomeDto homeViewData = movieService.getHomeViewData();

        // Verify that the result is not null
        assertEquals(0, homeViewData.getAvailableSize());
    }

    // Add more tests for other methods...

    @Test
    void testEditMovieNotFound() {
        // Mock data
        Long movieId = 1L;
        Movie editedMovie = new Movie();

        editedMovie.setTitle("Tony");
        editedMovie.setUrl("invalid_url");
        editedMovie.setPoster("poster_url");
        editedMovie.setReleaseYear(2000);
        editedMovie.setRuntime(15);
        editedMovie.setCast("Brad Pitt");
        editedMovie.setDirector("Zack Snyder");
        editedMovie.setDescription("And end");
        editedMovie.setGenre(GenreType.COMEDY);
        editedMovie.setCategory(CategoryType.PG_13);
        editedMovie.setRating(9.5);

        // Mock repository behavior
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        // Call the method and expect an exception
        assertThrows(NullPointerException.class, () -> movieService.editMovie(movieId, editedMovie));

        // Verify that save was not called
        verify(movieRepository, never()).save(any());
    }

    @Test
    void testEditMovie() {
        // Mock data
        Long movieId = 1L;
        Movie editedMovie = new Movie();

        editedMovie.setTitle("Tony");
        editedMovie.setUrl("invalid_url");
        editedMovie.setPoster("poster_url");
        editedMovie.setReleaseYear(2000);
        editedMovie.setRuntime(15);
        editedMovie.setCast("Brad Pitt");
        editedMovie.setDirector("Zack Snyder");
        editedMovie.setDescription("And end");
        editedMovie.setGenre(GenreType.COMEDY);
        editedMovie.setCategory(CategoryType.PG_13);
        editedMovie.setRating(9.5);

        // Mock repository behavior
        Movie existingMovie = new Movie();

        existingMovie.setTitle("TonyBit");
        existingMovie.setUrl("invalid_url");
        existingMovie.setPoster("poster_url");
        existingMovie.setReleaseYear(2000);
        existingMovie.setRuntime(15);
        existingMovie.setCast("Brad Pitt");
        existingMovie.setDirector("Zack Snyder");
        existingMovie.setDescription("And end");
        existingMovie.setGenre(GenreType.COMEDY);
        existingMovie.setCategory(CategoryType.PG_13);
        existingMovie.setRating(9.5);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any())).thenReturn(existingMovie);

        // Call the method
        movieService.editMovie(movieId, editedMovie);

        // Verify that save was called once
        verify(movieRepository, times(1)).save(existingMovie);
    }

    @Test
    void testGetMovieByReviewId_ReviewFound_ReturnsMovie() {
        // Arrange
        Long reviewId = 1L;
        Review review = new Review();
        Movie expectedMovie = new Movie();
        review.setMovie(expectedMovie);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // Act
        Movie result = movieService.getMovieByReviewId(reviewId);

        // Assert
        assertEquals(expectedMovie, result);
    }

    @Test
    void testGetMovieByReviewId_ReviewNotFound_ReturnsNull() {
        // Arrange
        Long reviewId = 1L;

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Act
        Movie result = movieService.getMovieByReviewId(reviewId);

        // Assert
        assertEquals(null, result);
    }

    @Test
    void testGetGenreViewData() {
        // Arrange
        GenreType genre = GenreType.ACTION;

        when(movieRepository.getMoviesByGenre(String.valueOf(genre))).thenReturn(new ArrayList<>());

        // Act
        MovieHomeDto result = movieService.getGenreViewData(genre);

        // Assert
        assertEquals(0, result.getAvailableSize());

    }
}
