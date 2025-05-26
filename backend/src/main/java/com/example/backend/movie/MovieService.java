package com.example.backend.movie;

import com.example.backend.external.TmdbClient;
import com.example.backend.review.Review;
import com.example.backend.review.ReviewRepository;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final TmdbClient tmdbClient;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

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

    public List<Movie> getMoviesReviewedByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다: " + userId));

        // 변경된 메서드 이름 사용
        List<Review> reviews = reviewRepository.findByWriterId((user.getId()));

        Set<Long> movieIds = reviews.stream()
                .map(Review::getMovieId)
                .collect(Collectors.toSet());

        return movieRepository.findAllById(movieIds);
    }
}
