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
    private Long id;

    @Column(length = 20, nullable = false)
    private String imdbCode;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255)
    private String director;

    private LocalDate releaseDate;

    @Column
    private String coverImage;
}
