package com.example.backend.movie.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private Long id;
    private String title;
    private Double rating;
    private String posterPath;
}
