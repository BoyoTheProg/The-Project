package com.movieapp.model.entity;
import com.movieapp.model.enums.CategoryType;
import com.movieapp.model.enums.GenreType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "movies")
public class Movie extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false, unique = true)
    private String url;
    @Column(nullable = false, unique = true)
    private String poster;
    @Column(nullable = false, name = "release_year")
    private int releaseYear;
    @Column(nullable = false, name = "runtime")
    private int runtime;
    @Column(nullable = false)
    private String cast;
    @Column(nullable = false)
    private String director;
    @Column(nullable = false)
    @Length(min = 2, max = 50)
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GenreType genre;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType category;
    @Column(nullable = false)
    @Min(1)
    @Max(10)
    private double rating;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

}

