package com.example.backend.apiCallTest.domain;

import lombok.Data;

@Data
public class Review {
    private Long id;
    private Long movieId;
    private String username;
    private String content;
    private int rating;
}
