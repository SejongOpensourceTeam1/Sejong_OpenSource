package com.example.backend.review.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long movieId;
    private String username;
    private String content;
    private Long rating;
    private LocalDate dateTime;
}
