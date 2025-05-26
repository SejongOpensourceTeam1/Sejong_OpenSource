package com.example.backend.movie;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;
    private final MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reviewed/{userId}")
    public ResponseEntity<List<Movie>> getReviewedMoviesByUserId(@PathVariable Long userId) {
        List<Movie> movies = movieService.getMoviesReviewedByUserId(userId);
        return ResponseEntity.ok(movies);
    }

}
