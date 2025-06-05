package com.example.backend.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter // Getter 메서드 자동 생성
@Setter // Setter 메서드 자동 생성
public class ReviewUpdateRequest {
    private String content; // 수정할 리뷰 내용
    private Long rating;    // 수정할 리뷰 평점
}
