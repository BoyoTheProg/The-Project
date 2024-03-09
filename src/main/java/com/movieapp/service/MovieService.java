package com.movieapp.service;

import com.movieapp.model.dto.movie.MovieAddBindingDto;
import com.movieapp.model.dto.movie.MovieDTO;
import com.movieapp.model.dto.movie.MovieDetailDto;
import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.model.entity.Movie;
import com.movieapp.model.entity.UserEntity;
import com.movieapp.model.enums.GenreType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieService {
    void add(MovieAddBindingDto movieAddBindingDto);

    void remove(Long id);

    MovieHomeDto getHomeViewData();

//    List<MovieDTO> getRecommendedMovies(Long userId, int count);
//
//    List<MovieDTO> getAllMoviesByGenre(GenreType genre);

    List<MovieDTO> getRecommendedMovies(Long userId);

    List<MovieDTO> getAllMoviesByGenre(GenreType genre);

    // Method to get a random movie poster for a given genre
    String getRandomMoviePoster(GenreType genre);

    MovieDetailDto getMovieViewData(Long id);

    Movie getMovieById(Long id);

    void editMovie(Long id, Movie editedMovie);

    Movie getMovieByReviewId(Long id);


    MovieHomeDto getGenreViewData(GenreType genre);
}
