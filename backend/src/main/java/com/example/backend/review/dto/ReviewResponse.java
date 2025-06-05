package com.example.backend.review.dto;

import com.example.backend.review.Review;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter // Getter 메서드 자동 생성
@Setter // Setter 메서드 자동 생성
public class ReviewResponse {
    private Long id; // 리뷰 고유 ID
    private Long movieId; // 리뷰 대상 영화 ID
    private String writer; // 리뷰 작성자 username
    private String content; // 리뷰 내용
    private Long rating; // 리뷰 평점
    private LocalDateTime dateTime; // 리뷰 작성 시간

    public ReviewResponse() {
        // 기본 생성자
    }

    public ReviewResponse(Long id, Long movieId, String writer, String content, Long rating, LocalDateTime dateTime) {
        // 모든 필드를 초기화하는 생성자
        this.id = id;
        this.movieId = movieId;
        this.writer = writer;
        this.content = content;
        this.rating = rating;
        this.dateTime = dateTime;
    }

    // Review 엔티티를 ReviewResponse DTO로 변환하는 정적 팩토리 메서드
    public static ReviewResponse fromEntity(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getMovieId(),
                review.getWriter().getUsername(),
                review.getContent(),
                review.getRating(),
                review.getDateTime()
        );
    }
}
