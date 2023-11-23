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
    private UserEntity userEntity;
    @ManyToOne
    private Movie movie;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
