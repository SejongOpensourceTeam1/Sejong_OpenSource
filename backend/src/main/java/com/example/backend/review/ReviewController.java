package com.example.backend.review;

import com.example.backend.review.dto.ReviewRequest;
import com.example.backend.user.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.backend.review.dto.ReviewResponse;
import com.example.backend.review.dto.ReviewUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest request) {
        log.info("리뷰 등록 요청 - movieId: {}, username: {}, content: {}, rating: {}, dateTime: {}",
                request.getMovieId(),
                request.getWriter(),
                request.getContent(),
                request.getRating(),
                request.getDateTime());

        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByMovieId(@PathVariable Long movieId) {
        List<ReviewResponse> reviews = reviewService.getReviewsByMovieId(movieId);
        return ResponseEntity.ok(reviews);
    }

    /*
    @GetMapping("/writer/{writerId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByWriterId(@PathVariable Long writerId) {
        List<ReviewResponse> reviews = reviewService.getReviewsByWriterId(writerId);
        return ResponseEntity.ok(reviews);
    }
     */

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewUpdateRequest request) {
        ReviewResponse updated = reviewService.updateReview(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
      
    }

}
