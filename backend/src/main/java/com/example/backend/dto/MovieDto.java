package com.example.backend.dto;

import lombok.Data;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private String overview;
    private String posterPath;
}
