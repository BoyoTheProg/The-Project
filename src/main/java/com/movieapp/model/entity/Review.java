package com.movieapp.model.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity{
    @Column
    @Length(min = 3)
    private String review;
    @ManyToOne
    private User user;
    @ManyToOne
    private Movie movie;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
