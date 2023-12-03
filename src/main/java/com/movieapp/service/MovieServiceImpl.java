package com.movieapp.service;

import com.movieapp.model.dto.movie.MovieAddBindingDto;
import com.movieapp.model.dto.movie.MovieDTO;
import com.movieapp.model.dto.movie.MovieDetailDto;
import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.Review;
import com.movieapp.repo.MovieRepository;
import com.movieapp.repo.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void add(MovieAddBindingDto movieAddBindingDto) {
//        Movie movie = movieRepository.findByTitle(movieAddBindingDto.getTitle());


        if (movieRepository.count() >= 0) {
            Movie movie = new Movie();
            movie.setTitle(movieAddBindingDto.getTitle());
            movie.setCast(movieAddBindingDto.getCast());
            movie.setDirector(movieAddBindingDto.getDirector());
            movie.setUrl(movieAddBindingDto.getUrl());
            movie.setDescription(movieAddBindingDto.getDescription());
            movie.setReleaseYear(movieAddBindingDto.getReleaseYear());
            movie.setPoster(movieAddBindingDto.getPoster());
            movie.setCategory(movieAddBindingDto.getCategory());
            movie.setGenre(movieAddBindingDto.getGenre());
            movie.setRating(movieAddBindingDto.getRating());
            movie.setRuntime(movieAddBindingDto.getRuntime());

            movieRepository.save(movie);
        }
    }

    @Override
    public void remove(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public MovieHomeDto getHomeViewData() {
        List<MovieDTO> availableMovies = movieRepository.getAllAvailable().stream()
                .map(MovieDTO::createFromMovie)
                .toList();

        return new MovieHomeDto(availableMovies);
    }

    @Override
    public MovieDetailDto getMovieViewData(Long id) {
        MovieDTO movie = MovieDTO.createFromMovie(movieRepository.getById(id));

        return new MovieDetailDto(
                movie.getTitle(),
                movie.getUrl(),
                movie.getPoster(),
                movie.getReleaseYear(),
                movie.getRuntime(),
                movie.getCast(),
                movie.getDirector(),
                movie.getDescription(),
                movie.getGenre(),
                movie.getCategory(),
                movie.getRating()
        );
    }

    @Override
    @Transactional
    public Movie getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);

        return movie;
    }

    @Override
    public void editMovie(Long id, Movie editedMovie) {
        // Retrieve the existing movie from the database
        Movie existingMovie = movieRepository.findById(id).orElseThrow(() -> new NullPointerException("Movie not found"));

        // Update the fields of the existing movie with the edited values
        existingMovie.setTitle(editedMovie.getTitle());
        existingMovie.setReleaseYear(editedMovie.getReleaseYear());
        existingMovie.setRuntime(editedMovie.getRuntime());
        existingMovie.setCast(editedMovie.getCast());
        existingMovie.setDirector(editedMovie.getDirector());
        existingMovie.setDescription(editedMovie.getDescription());
        existingMovie.setGenre(editedMovie.getGenre());
        existingMovie.setCategory(editedMovie.getCategory());
        existingMovie.setRating(editedMovie.getRating());

        // Save the updated movie back to the database
        movieRepository.save(existingMovie);
    }

    @Override
    public Movie getMovieByReviewId(Long reviewId) {
        // Fetch the review by ID
        Review review = reviewRepository.findById(reviewId).orElse(null);

        // Check if the review is not null and has an associated movie
        if (review != null && review.getMovie() != null) {
            return review.getMovie();
        }

        // Return null if the review or associated movie is not found
        return null;
    }
}

