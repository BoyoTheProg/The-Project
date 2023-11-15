package com.movieapp.service;

import com.movieapp.model.dto.movie.MovieAddBindingDto;
import com.movieapp.model.dto.movie.MovieDetailDto;
import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.model.entity.Movie;

public interface MovieService {
    void add(MovieAddBindingDto movieAddBindingDto);

    void remove(Long id);

    MovieHomeDto getHomeViewData();

    MovieDetailDto getMovieViewData(Long id);

    Movie getMovieById(Long id);
}
