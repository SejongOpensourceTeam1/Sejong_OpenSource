package com.example.backend.review;

import com.example.backend.review.Review;
import com.example.backend.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public String createReview(@ModelAttribute Review review, Model model) {
        reviewService.create(review);
        model.addAttribute("message", "리뷰가 성공적으로 작성되었습니다.");
        return "reviewForm";
    }

    @GetMapping("/movie/{movieId}")
    public String getReviewsByMovieId(@PathVariable Long movieId, Model model) {
        List<Review> reviews = reviewService.findByMovieId(movieId);
        model.addAttribute("reviews", reviews);
        return "movieReviews";
    }
}
