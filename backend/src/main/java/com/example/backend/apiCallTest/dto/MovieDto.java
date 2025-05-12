package com.example.backend.apiCallTest.dto;

import lombok.Data;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private String overview;
    private String posterPath;
}
