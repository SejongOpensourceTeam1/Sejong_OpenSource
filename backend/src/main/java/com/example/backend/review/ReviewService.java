package com.example.backend.review;

import com.example.backend.external.TmdbClient;
import com.example.backend.movie.MovieRepository;
import com.example.backend.user.UserRepository;
import com.example.backend.review.dto.ReviewRequest;
import com.example.backend.review.dto.ReviewResponse;
import com.example.backend.review.dto.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final TmdbClient tmdbClient;

    // 리뷰 작성 처리
    public ReviewResponse createReview(ReviewRequest request) {
        // 작성자(User)를 DB에서 조회, 없으면 예외 발생
        var writer = userRepository.findByUsername(request.getWriter())
                .orElseThrow(() -> new IllegalArgumentException("작성자가 존재하지 않습니다."));

        // ⭐ 영화가 DB에 없으면 TMDB에서 가져와 캐싱 처리
        if (!movieRepository.existsById(request.getMovieId())) {
            var movie = tmdbClient.fetchMovieById(request.getMovieId());
            if (movie != null) {
                movieRepository.save(movie);
            }
        }

        // 리뷰 엔티티 생성 후 저장
        var review = new Review(
                request.getMovieId(),
                writer,
                request.getContent(),
                request.getRating(),
                request.getDateTime()
        );
        var savedReview = reviewRepository.save(review);

        // 저장된 리뷰 엔티티를 DTO로 변환 후 반환
        return ReviewResponse.fromEntity(savedReview);
    }

    // 특정 영화 ID에 대한 리뷰 목록 조회 (읽기 전용 트랜잭션)
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 리뷰 수정 처리
    public ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request) {
        // 리뷰 조회, 없으면 예외 발생
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        // 수정 요청된 필드가 있으면 업데이트
        if (request.getContent() != null) {
            review.setContent(request.getContent());
        }
        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }

        // 변경된 리뷰를 DTO로 변환 후 반환
        return ReviewResponse.fromEntity(review);
    }

    // 리뷰 삭제 처리
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
        reviewRepository.delete(review);
    }

}
