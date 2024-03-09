package com.movieapp.model.dto.movie;


import com.movieapp.model.entity.Movie;
import com.movieapp.model.enums.CategoryType;
import com.movieapp.model.enums.GenreType;

public class MovieDTO {
    private Long id;
    private String title;
    private String url;
    private String poster;
    private String slidePoster;
    private int releaseYear;
    private int runtime;
    private String cast;
    private String director;
    private String description;
    private GenreType genre;
    private CategoryType category;
    private double rating;
    private String formattedRuntime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public String getSlidePoster() {
        return slidePoster;
    }

    public void setSlidePoster(String slidePoster) {
        this.slidePoster = slidePoster;
    }

    public String getFormattedRuntime() {
        return formattedRuntime;
    }

    public void setFormattedRuntime(String formattedRuntime) {
        this.formattedRuntime = formattedRuntime;
    }

    public static MovieDTO createFromMovie(Movie movie){
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setUrl(movie.getUrl());
        movieDTO.setPoster(movie.getPoster());
        movieDTO.setSlidePoster(movie.getSlidePoster());
        movieDTO.setReleaseYear(movie.getReleaseYear());
        movieDTO.setRuntime(movie.getRuntime());
        movieDTO.setCast(movie.getCast());
        movieDTO.setDirector(movie.getDirector());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setGenre(movie.getGenre());
        movieDTO.setCategory(movie.getCategory());
        movieDTO.setRating(movie.getRating());

        int runtime = movie.getRuntime();
        int hours = runtime / 60;  // Get the whole number of hours
        int minutes = runtime % 60;
        movieDTO.setFormattedRuntime(hours + "h " + minutes + "min");

        return movieDTO;
    }

    public Movie toMovie() {
        Movie movie = new Movie();

        movie.setId(this.getId());
        movie.setTitle(this.getTitle());
        movie.setUrl(this.getUrl());
        movie.setPoster(this.getPoster());
        movie.setSlidePoster(this.getSlidePoster());
        movie.setReleaseYear(this.getReleaseYear());
        movie.setRuntime(this.getRuntime());
        movie.setCast(this.getCast());
        movie.setDirector(this.getDirector());
        movie.setDescription(this.getDescription());
        movie.setGenre(this.getGenre());
        movie.setCategory(this.getCategory());
        movie.setRating(this.getRating());

        return movie;
    }
}
