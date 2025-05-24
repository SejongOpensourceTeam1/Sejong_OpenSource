package com.example.backend.movie;

import com.example.backend.external.TmdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final TmdbClient tmdbClient;

    public Movie getOrFetchMovie(Long movieId) {
        // 1. DB에 캐시된 영화 있는지 확인
        Optional<Movie> cachedMovie = movieRepository.findById(movieId);
        if (cachedMovie.isPresent()) {
            return cachedMovie.get();
        }

        // 2. 없으면 TMDB에서 fetch
        Movie fetchedMovie = tmdbClient.fetchMovieById(movieId);
        if (fetchedMovie == null) {
            throw new IllegalArgumentException("영화 정보를 TMDB에서 찾을 수 없습니다. ID: " + movieId);
        }

        // 3. DB에 저장 후 반환
        return movieRepository.save(fetchedMovie);
    }
}
