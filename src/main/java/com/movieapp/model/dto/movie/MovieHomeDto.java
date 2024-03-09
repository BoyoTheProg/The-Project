package com.movieapp.model.dto.movie;

import java.util.ArrayList;
import java.util.List;

public class MovieHomeDto {

    private List<MovieDTO> availableMovies;
    private List<MovieDTO> latestMovies;
    private List<MovieDTO> lastWatchedMovies;
    private List<MovieDTO> recommendedMovies;


    private int availableSize;

    public MovieHomeDto(List<MovieDTO> availableMovies, List<MovieDTO> latestMovies, List<MovieDTO> lastWatchedMovies, List<MovieDTO> recommendedMovies) {
        this.availableMovies = availableMovies;
        this.latestMovies = latestMovies;
        this.lastWatchedMovies = lastWatchedMovies;
        this.recommendedMovies = recommendedMovies;
    }

    public MovieHomeDto(List<MovieDTO> availableMovies) {
        this.availableMovies = availableMovies;
        this.availableSize = availableMovies.size();
    }

    public MovieHomeDto(List<MovieDTO> availableMovies, List<MovieDTO> lastWatchedMovies, List<MovieDTO> recommendedMovies) {
        this.availableMovies = availableMovies;
        this.lastWatchedMovies = lastWatchedMovies;
        this.recommendedMovies = recommendedMovies;
    }

    public List<MovieDTO> getAvailableMovies() {
        return availableMovies;
    }

    public int getAvailableSize() {
        return availableSize;
    }

    public List<MovieDTO> getLastWatchedMovies() {
        return lastWatchedMovies;
    }

    public List<MovieDTO> getRecommendedMovies() {
        return recommendedMovies;
    }

    public List<MovieDTO> getLatestMovies() {
        return latestMovies;
    }
}
