package com.movieapp.service;

import com.movieapp.model.dto.movie.MovieAddBindingDto;
import com.movieapp.model.dto.movie.MovieDTO;
import com.movieapp.model.dto.movie.MovieDetailDto;
import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.Review;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.model.entity.UserMovieInteraction;
import com.movieapp.model.enums.GenreType;
import com.movieapp.repo.MovieRepository;
import com.movieapp.repo.ReviewRepository;
import com.movieapp.repo.UserMovieInteractionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final UserMovieInteractionRepository userMovieInteractionRepository;
    private final UserService userService;

    public MovieServiceImpl(MovieRepository movieRepository, ReviewRepository reviewRepository, UserMovieInteractionRepository userMovieInteractionRepository, UserService userService) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.userMovieInteractionRepository = userMovieInteractionRepository;
        this.userService = userService;
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
            movie.setSlidePoster(movieAddBindingDto.getSlidePoster());
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
    @Transactional
    public MovieHomeDto getHomeViewData() {
        try {
            UserEntity currentUser = userService.getCurrentUser();

            List<MovieDTO> availableMovies = movieRepository.getAllAvailable().stream()
                    .map(MovieDTO::createFromMovie)
                    .collect(Collectors.toList());

            List<MovieDTO> latestMovies = movieRepository.getLatestMovies().stream()
                    .map(MovieDTO::createFromMovie)
                    .collect(Collectors.toList());

            List<MovieDTO> lastWatchedMovies = getLastWatchedMovies(currentUser.getId());

            List<MovieDTO> recommendedMovies = getRecommendedMovies(currentUser.getId());

            if (lastWatchedMovies.isEmpty()){
                lastWatchedMovies = availableMovies;
            }
            // Return MovieHomeDto with available, last watched, and recommended movies
            return new MovieHomeDto(availableMovies,latestMovies, lastWatchedMovies, recommendedMovies);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            // Return an empty MovieHomeDto or throw a custom exception
            return new MovieHomeDto(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        }
    }

    public List<MovieDTO> getLastWatchedMovies(Long userId) {
        // Get the last five interactions for the specified user
        UserEntity user = userService.getCurrentUser();
        List<UserMovieInteraction> lastFiveInteractions = userMovieInteractionRepository.findLastFiveInteractions(user.getId());

        // Map Movie entities to MovieDTOs
        List<MovieDTO> lastWatchedMoviesDTO = lastFiveInteractions.stream()
                .map(UserMovieInteraction::getMovie) // Get the Movie entity from each interaction
                .map(MovieDTO::createFromMovie) // Map the Movie entity to MovieDTO
                .collect(Collectors.toList());

        return lastWatchedMoviesDTO;
    }


    @Override
    public List<MovieDTO> getRecommendedMovies(Long userId) {
        UserEntity user = userService.getCurrentUser();
        // Get the last watched movies for the user
        List<MovieDTO> lastWatchedMovies = getLastWatchedMovies(user.getId());

        // Calculate the most common genre in the last watched movies
        Map<GenreType, Long> genreFrequencyMap = lastWatchedMovies.stream()
                .collect(Collectors.groupingBy(MovieDTO::getGenre, Collectors.counting()));

        // Find the most frequent genre
        Optional<Map.Entry<GenreType, Long>> mostFrequentGenreEntry = genreFrequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        // Retrieve recommended movies of the most common genre
        GenreType mostFrequentGenre = mostFrequentGenreEntry.map(Map.Entry::getKey).orElse(GenreType.ACTION);
        List<MovieDTO> recommendedMoviesDTO = getAllMoviesByGenre(mostFrequentGenre);

        return recommendedMoviesDTO;
    }

    @Override
    public List<MovieDTO> getAllMoviesByGenre(GenreType genre) {
        return movieRepository.getMoviesByGenre(genre.toString()).stream()
                .map(MovieDTO::createFromMovie)
                .collect(Collectors.toList());
    }

    // Method to get a random movie poster for a given genre
    @Override
    public String getRandomMoviePoster(GenreType genre) {
        List<MovieDTO> movies = getAllMoviesByGenre(genre);
        if (movies.isEmpty()) {
            // Handle case when there are no movies for the given genre
            return ""; // Or provide a default poster
        }
        Random random = new Random();
        MovieDTO randomMovie = movies.get(random.nextInt(movies.size()));
        return randomMovie.getSlidePoster();
    }

    @Override
    public MovieDetailDto getMovieViewData(Long id) {
        MovieDTO movie = MovieDTO.createFromMovie(movieRepository.getById(id));

        return new MovieDetailDto(
                movie.getTitle(),
                movie.getUrl(),
                movie.getPoster(),
                movie.getSlidePoster(),
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
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));

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


    @Override
    public MovieHomeDto getGenreViewData(GenreType genre){
        List<MovieDTO> availableMovies = movieRepository.getMoviesByGenre(String.valueOf(genre)).stream()
                .map(MovieDTO::createFromMovie)
                .toList();

        return new MovieHomeDto(availableMovies);
    }

}

