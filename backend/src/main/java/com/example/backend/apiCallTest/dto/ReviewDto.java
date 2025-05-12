package com.example.backend.apiCallTest.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private Long movieId;
    private String username;
    private String content;
    private int rating;
}
