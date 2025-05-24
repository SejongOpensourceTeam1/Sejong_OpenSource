package com.example.backend.review;

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

    public ReviewResponse createReview(ReviewRequest request) {
        var writer = userRepository.findByUsername(request.getWriter())
                .orElseThrow(() -> new IllegalArgumentException("작성자가 존재하지 않습니다."));

        var review = new Review(request.getMovieId(), writer, request.getContent(), request.getRating(), request.getDateTime());
        var savedReview = reviewRepository.save(review);

        return ReviewResponse.fromEntity(savedReview);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByWriterId(Long writerId) {
        List<Review> reviews = reviewRepository.findByWriterId(writerId);
        return reviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        if (request.getContent() != null) {
            review.setContent(request.getContent());
        }
        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }

        return ReviewResponse.fromEntity(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
        reviewRepository.delete(review);
    }
}
