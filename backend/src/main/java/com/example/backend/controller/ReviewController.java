package com.example.backend.controller;

import com.example.backend.domain.Review;
import com.example.backend.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
