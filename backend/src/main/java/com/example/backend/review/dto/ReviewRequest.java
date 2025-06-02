package com.example.backend.review.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long movieId;
    private String writer;
    private String content;
    private Long rating;
    private LocalDateTime dateTime;
}
