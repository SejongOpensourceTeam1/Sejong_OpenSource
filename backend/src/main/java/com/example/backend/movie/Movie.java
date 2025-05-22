package com.example.backend.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 내부 DB용 식별자

    @Column(nullable = false, unique = true)
    private Long movieId; // TMDB 고유 영화 ID

    @Column(length = 20)
    private String imdbCode;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255)
    private String director;

    private LocalDate releaseDate;

    @Column
    private String coverImage;
}
