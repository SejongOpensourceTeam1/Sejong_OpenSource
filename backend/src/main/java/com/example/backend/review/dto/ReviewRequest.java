package com.example.backend.review.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter // Getter 메서드 자동 생성
@Setter // Setter 메서드 자동 생성
public class ReviewRequest {
    private Long movieId; // 리뷰 대상 영화의 ID
    private String writer; // 리뷰 작성자 (username)
    private String content; // 리뷰 내용
    private Long rating; // 리뷰 평점
    private LocalDateTime dateTime; // 리뷰 작성 시간
}
