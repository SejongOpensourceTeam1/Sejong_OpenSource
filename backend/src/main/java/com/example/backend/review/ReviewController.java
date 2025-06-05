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

@Slf4j // 로그를 쉽게 남기기 위한 Lombok 어노테이션
@RestController // REST API 컨트롤러임을 선언
@RequestMapping("/reviews") // /reviews 경로 매핑
@RequiredArgsConstructor // final 필드 생성자 자동 생성
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest request) {
        // 리뷰 등록 요청 처리 메서드
        log.info("리뷰 등록 요청 - movieId: {}, username: {}, content: {}, rating: {}, dateTime: {}",
                request.getMovieId(),
                request.getWriter(),
                request.getContent(),
                request.getRating(),
                request.getDateTime());

        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.ok(response); // 200 OK와 생성된 리뷰 반환
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByMovieId(@PathVariable Long movieId) {
        // 특정 영화에 대한 리뷰 리스트 조회
        List<ReviewResponse> reviews = reviewService.getReviewsByMovieId(movieId);
        return ResponseEntity.ok(reviews); // 200 OK와 리뷰 리스트 반환
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewUpdateRequest request) {
        // 특정 리뷰 수정 처리
        ReviewResponse updated = reviewService.updateReview(id, request);
        return ResponseEntity.ok(updated); // 200 OK와 수정된 리뷰 반환
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        // 특정 리뷰 삭제 처리
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }

}
