package com.example.backend.review.dto;

public record ReviewRequest(Long movieId, Long writerId, String content) { }
