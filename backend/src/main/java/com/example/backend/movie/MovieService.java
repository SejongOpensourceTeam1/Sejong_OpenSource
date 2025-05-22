package com.example.backend.movie;

import com.example.backend.external.TmdbClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final TmdbClient tmdbClient;

    public MovieService(MovieRepository movieRepository, TmdbClient tmdbClient) {
        this.movieRepository = movieRepository;
        this.tmdbClient = tmdbClient;
    }

    // TMDB movieId로 조회
    public Optional<Movie> findByMovieId(Long movieId) {
        return movieRepository.findByMovieId(movieId);
    }

    // 리뷰 등록 시 사용: 이미 있으면 조회, 없으면 저장
    public Movie saveIfNotExists(Long movieId) {
        return movieRepository.findByMovieId(movieId)
                .orElseGet(() -> {
                    Movie movie = tmdbClient.fetchMovieById(movieId);
                    if (movie == null) {
                        throw new IllegalArgumentException("해당 영화 정보를 찾을 수 없습니다.");
                    }
                    return movieRepository.save(movie);
                });
    }

    // ID로 조회 (기본 키)
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }
}
