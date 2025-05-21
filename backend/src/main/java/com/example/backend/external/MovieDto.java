package com.example.backend.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {

    private Long id;

    private String title;

    @JsonProperty("imdb_id")
    private String imdbId;

    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;  // yyyy-MM-dd 형식 문자열, 나중에 LocalDate로 변환 가능

    @JsonProperty("poster_path")
    private String posterPath;

}
