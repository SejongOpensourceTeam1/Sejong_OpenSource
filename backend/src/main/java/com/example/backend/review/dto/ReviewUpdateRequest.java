package com.example.backend.review.dto;

public record ReviewUpdateRequest(
        String newContent,
        Long newRating   // 수정 시 평점도 변경 가능하도록 추가
) {}
