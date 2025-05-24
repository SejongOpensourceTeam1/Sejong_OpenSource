package com.example.backend.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // TMDB movieId로 영화 조회
    @GetMapping("/tmdb/{movieId}")
    public ResponseEntity<Movie> getMovieByTmdbId(@PathVariable Long movieId) {
        return movieService.findByMovieId(movieId)
                .map(movie -> ResponseEntity.ok(movie))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 내부 DB id로 영화 조회
    @GetMapping("/{id}")
    public Optional<Movie> getMovieById(@PathVariable Long id) {
        return movieService.findById(id);
    }
}
