package com.example.backend.review;

import com.example.backend.review.dto.ReviewRequest;
import com.example.backend.review.dto.ReviewUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 특정 영화 리뷰 조회 (프론트엔드에서 영화 ID 라우팅 관리)
    @GetMapping("/movie/{movieId}")
    public List<Review> getReviewsByMovie(@PathVariable Long movieId) {
        return reviewService.getByMovieId(movieId);
    }

    // 전체 리뷰 조회 (관리용)
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAll();
    }

    // 리뷰 생성 (movieId, writerId, content, rating 포함)
    @PostMapping
    public Review createReview(@RequestBody ReviewRequest request) {
        return reviewService.create(
                request.movieId(),
                request.writerId(),
                request.content(),
                request.rating()
        );
    }

    // 리뷰 수정 (내용 및 평점 수정 가능)
    @PutMapping("/{reviewId}")
    public Review updateReview(@PathVariable Long reviewId,
                               @RequestBody ReviewUpdateRequest request) {
        return reviewService.update(reviewId, request.newContent(), request.newRating());
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
    }
}
