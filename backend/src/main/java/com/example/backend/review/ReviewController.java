package com.example.backend.review;

import com.example.backend.movie.Movie;
import com.example.backend.movie.MovieRepository;
import com.example.backend.review.Review;
import com.example.backend.review.ReviewService;
import com.example.backend.review.dto.ReviewRequest;
import com.example.backend.review.dto.UserReviewResponse;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import com.example.backend.user.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

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

    @GetMapping("/my")
    public ResponseEntity<List<UserReviewResponse>> getMyReviews(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(reviewService.getMyReviewMovies(userId));
    }

}
