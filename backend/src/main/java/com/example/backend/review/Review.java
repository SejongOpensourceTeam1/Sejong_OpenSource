package com.example.backend.review;

import com.example.backend.movie.Movie;
import com.example.backend.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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
    private User writer;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column
    private Long rating;

    public Review(Movie movie, User writer, String content, Long rating) {
        this.movie = movie;
        this.writer = writer;
        this.content = content;
        this.rating = rating;
        this.dateTime = LocalDateTime.now();
    }
}
