package com.example.backend.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserReviewResponse {
    private Long movieId;
    private String title;
    private String coverImage;
    private String rating;
    private String content;
}
