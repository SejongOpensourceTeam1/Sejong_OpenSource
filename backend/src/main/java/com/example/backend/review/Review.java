package com.example.backend.review;

import com.example.backend.movie.Movie;
import com.example.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User username;

    @Column(length = 1000, nullable = false)
    private String content;

    private String rating;

    public Review(Movie movie, User username, String content) {
        this.movie = movie;
        this.username = username;
        this.content = content;
    }
}
