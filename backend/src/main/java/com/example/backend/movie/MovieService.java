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

@Service // 해당 클래스가 서비스 레이어의 컴포넌트임을 나타냄
@RequiredArgsConstructor // final 필드들을 파라미터로 받는 생성자 자동 생성
public class MovieService {

    private final MovieRepository movieRepository; // 영화 정보를 다루는 JPA 레포지토리
    private final TmdbClient tmdbClient; // TMDB 외부 API 클라이언트
    private final ReviewRepository reviewRepository; // 리뷰 정보를 다루는 레포지토리
    private final UserRepository userRepository; // 유저 정보를 다루는 레포지토리

    public Movie getOrFetchMovie(Long movieId) {
        // 1. DB에 영화 정보가 있는지 확인 (캐싱)
        Optional<Movie> cachedMovie = movieRepository.findById(movieId);
        if (cachedMovie.isPresent()) {
            return cachedMovie.get(); // 있으면 그대로 반환
        }

        // 2. TMDB API에서 영화 정보 가져오기
        Movie fetchedMovie = tmdbClient.fetchMovieById(movieId);
        if (fetchedMovie == null) {
            throw new IllegalArgumentException("영화 정보를 TMDB에서 찾을 수 없습니다. ID: " + movieId);
        }

        // 3. 가져온 영화 정보를 DB에 저장한 후 반환
        return movieRepository.save(fetchedMovie);
    }

    public List<Movie> getMoviesReviewedByUserId(String userId) {
        // 사용자 ID로 유저 조회 (없으면 예외 발생)
        User user = userRepository.findByUsername(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다: " + userId));

        // 해당 유저가 작성한 리뷰 목록 조회
        List<Review> reviews = reviewRepository.findByWriter(user);

        // 리뷰에서 영화 ID만 추출
        Set<Long> movieIds = reviews.stream()
                .map(Review::getMovieId)
                .collect(Collectors.toSet());

        // 해당 영화 ID 목록으로 영화 리스트 조회
        return movieRepository.findAllById(movieIds);
    }
}
