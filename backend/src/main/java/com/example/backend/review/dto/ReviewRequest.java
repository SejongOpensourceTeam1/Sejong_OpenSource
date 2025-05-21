package com.example.backend.review.dto;

import java.time.LocalDate;

public record ReviewRequest(
        Long movieId,
        Long writerId,
        String content,
        Long rating
) {}
