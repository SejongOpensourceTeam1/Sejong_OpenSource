package com.example.backend.review;

import com.example.backend.movie.Movie;
import com.example.backend.movie.MovieService;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieService movieService;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         MovieService movieService,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.movieService = movieService;
        this.userRepository = userRepository;
    }

    public Review create(Long movieId, Long userId, String content, Long rating) {
        // 영화 저장 또는 조회 (캐싱)
        Movie movie = movieService.saveIfNotExists(movieId);

        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 리뷰 생성 (생성자에서 LocalDateTime.now() 처리)
        Review review = new Review(movie, user, content, rating);

        return reviewRepository.save(review);
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public List<Review> getByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    @Transactional
    public Review update(Long reviewId, String newContent, Long newRating) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));

        review.setContent(newContent);
        review.setRating(newRating);

        return review;
    }

    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
