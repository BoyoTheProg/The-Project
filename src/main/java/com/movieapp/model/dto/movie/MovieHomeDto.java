package com.movieapp.model.dto.movie;

import java.util.ArrayList;
import java.util.List;

public class MovieHomeDto {

    private List<MovieDTO> availableMovies;


    private int availableSize;

    public MovieHomeDto() {
        this(new ArrayList<>());
    }

    public MovieHomeDto(List<MovieDTO> availableMovies) {
        this.availableMovies = availableMovies;
        this.availableSize = availableMovies.size();
    }

    public List<MovieDTO> getAvailableMovies() {
        return availableMovies;
    }

    public int getAvailableSize() {
        return availableSize;
    }
}
