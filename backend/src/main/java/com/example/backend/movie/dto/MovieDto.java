package com.example.backend.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private Long id;
    private String title;
    @JsonProperty("vote_average")
    private Number rating;
    @JsonProperty("poster_path")
    private String posterPath;
}
