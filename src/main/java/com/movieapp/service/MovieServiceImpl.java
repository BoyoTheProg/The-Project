package com.movieapp.service;

import com.movieapp.model.dto.movie.MovieAddBindingDto;
import com.movieapp.model.dto.movie.MovieDTO;
import com.movieapp.model.dto.movie.MovieDetailDto;
import com.movieapp.model.dto.movie.MovieHomeDto;
import com.movieapp.model.entity.Movie;
import com.movieapp.repo.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void add(MovieAddBindingDto movieAddBindingDto) {
//        Movie movie = movieRepository.findByTitle(movieAddBindingDto.getTitle());


        if (movieRepository.count() >= 0){
            Movie movie= new Movie();
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
    public MovieDetailDto getMovieViewData(Long id){
        MovieDTO movie = MovieDTO.createFromMovie(movieRepository.getById(id));

        return new MovieDetailDto(
                movie.getTitle(),
                movie.getUrl(),
                movie.getPoster(),
                movie.getReleaseYear(),
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
}
