package com.example.backend.movie;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    private Long id; // TMDB 영화 고유 ID

    @Column(nullable = false)
    private String title; // 영화 제목

    @Column(name = "poster_path")
    private String posterPath; // 포스터 경로

    @Column(name = "rating", precision = 3)
    private Double rating; // 평점 (예: 8.3)
}
