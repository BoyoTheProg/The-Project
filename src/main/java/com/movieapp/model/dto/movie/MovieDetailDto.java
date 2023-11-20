package com.movieapp.model.dto.movie;

import com.movieapp.model.enums.CategoryType;
import com.movieapp.model.enums.GenreType;

public class MovieDetailDto {
    private String title;
    private String url;
    private String poster;
    private int releaseYear;
    private int runtime;
    private String cast;
    private String director;
    private String description;
    private GenreType genre;
    private CategoryType category;
    private double rating;

    public MovieDetailDto() {
    }

    public MovieDetailDto(String title, String url, String poster, int releaseYear, int runtime, String cast, String director, String description, GenreType genre, CategoryType category, double rating) {
        this.title = title;
        this.url = url;
        this.poster = poster;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.cast = cast;
        this.director = director;
        this.description = description;
        this.genre = genre;
        this.category = category;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getPoster() {
        return poster;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getCast() {
        return cast;
    }

    public String getDirector() {
        return director;
    }

    public String getDescription() {
        return description;
    }

    public GenreType getGenre() {
        return genre;
    }

    public CategoryType getCategory() {
        return category;
    }

    public double getRating() {
        return rating;
    }

    public int getRuntime() {
        return runtime;
    }
}
