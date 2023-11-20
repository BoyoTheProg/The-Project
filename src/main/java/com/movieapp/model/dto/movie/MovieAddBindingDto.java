package com.movieapp.model.dto.movie;


import com.movieapp.model.enums.CategoryType;
import com.movieapp.model.enums.GenreType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.Year;


public class MovieAddBindingDto {
    private final int year = Year.now().getValue();

    @NotNull(message = "You must enter the title!")
    private String title;
    @NotNull(message = "You must enter the url!")
    private String url;
    @NotNull(message = "You must enter the poster url!")
    private String poster;
    @NotNull(message = "You must enter the year! Year must not be in the future!")
    @Min(1900)
    @Max(2023)
    private int releaseYear;
    @NotNull(message = "You must enter the runtime!")
    @Min(1)
    private int runtime;
    @NotNull(message = "You must enter the cast")
    private String cast;
    @NotNull(message = "You must enter the director!")
    private String director;
    @NotNull
    @Length(min = 2, max = 50,  message = "Description length must be between 2 and 50 characters!")
    private String description;
    @NotNull(message = "You must select a genre!")
    private GenreType genre;
    @NotNull(message = "You must select a category!")
    private CategoryType category;
    @NotNull(message = "Rating must be between 1 and 10!")
    @Min(1)
    @Max(10)
    private double rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GenreType getGenre() {
        return genre;
    }

    public void setGenre(GenreType genre) {
        this.genre = genre;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
}
