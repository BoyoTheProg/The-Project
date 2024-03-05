package com.movieapp.service;

import com.movieapp.model.dto.movie.MovieDTO;
import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.model.entity.UserMovieInteraction;
import com.movieapp.model.enums.GenreType;
import com.movieapp.repo.MovieRepository;
import com.movieapp.repo.UserMovieInteractionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieInteractionServiceImpl implements UserMovieInteractionService {
    private final UserMovieInteractionRepository userMovieInteractionRepository;
    private final UserService userService; // Inject UserService for obtaining the current user.
    private final MovieRepository movieRepository;

    public UserMovieInteractionServiceImpl(UserMovieInteractionRepository userMovieInteractionRepository, UserService userService, MovieRepository movieRepository) {
        this.userMovieInteractionRepository = userMovieInteractionRepository;
        this.userService = userService;
        this.movieRepository = movieRepository;
    }

    @Override
    @Transactional
    public void saveUserMovieInteraction(Long movieId, boolean watched) {
        UserEntity currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            Movie movie = movieRepository.getById(movieId);

            if (movie != null) {
                UserMovieInteraction interaction = new UserMovieInteraction();
                interaction.setUser(currentUser);
                interaction.setMovie(movie);
                interaction.setTimestamp(LocalDateTime.now()); // Set the timestamp to the current date and time

                userMovieInteractionRepository.save(interaction);

                currentUser.getMovieInteractions().add(interaction);
            }
        }
    }

    @Override
    public List<UserMovieInteraction> findLastFiveInteractions(Long userId) {
        UserEntity user = userService.getCurrentUser();

        List<UserMovieInteraction> userInteractions = userMovieInteractionRepository
                .findLastFiveInteractions(user.getId());

        return userInteractions;
    }




    public List<MovieDTO> getAllMoviesByGenre(GenreType genre) {
        try {
            GenreType parsedGenre = Enum.valueOf(GenreType.class, genre.toString());
            return movieRepository.getMoviesByGenre(parsedGenre.toString()).stream()
                    .map(MovieDTO::createFromMovie)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            // Handle the case where the provided genre string is not a valid enum constant
            // You might want to log an error or throw a specific exception
            return Collections.emptyList();
        }
    }

}

