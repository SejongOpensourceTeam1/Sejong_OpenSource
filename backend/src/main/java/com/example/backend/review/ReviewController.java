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

    @GetMapping("/movie/{movieId}")
    public List<Review> getReviewsByMovie(@PathVariable Long movieId) {
        return reviewService.getByMovieId(movieId);
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAll();
    }

    /*
    @PostMapping
    public Review createReview(@RequestBody ReviewRequest request) {
        return reviewService.create(request.movieId(), request.writerId(), request.content());
    }
    */

    @PutMapping("/{reviewId}")
    public Review updateReview(@PathVariable Long reviewId, @RequestBody ReviewUpdateRequest request) {
        return reviewService.update(reviewId, request.newContent());
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
    }
}
