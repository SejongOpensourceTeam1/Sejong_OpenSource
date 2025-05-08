package com.example.backend.review;

import com.example.backend.movie.Movie;
import com.example.backend.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Movie movie;

    private String content;
    private int rating;
}
