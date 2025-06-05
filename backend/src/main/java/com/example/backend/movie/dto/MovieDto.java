package com.example.backend.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter // 각 필드에 대한 getter 메서드 자동 생성
@Setter // 각 필드에 대한 setter 메서드 자동 생성
public class MovieDto {
    private Long id; // 영화 ID
    private String title; // 영화 제목

    @JsonProperty("vote_average") // JSON의 "vote_average" 값을 rating 필드에 매핑
    private Number rating; // 영화 평점

    @JsonProperty("poster_path") // JSON의 "poster_path" 값을 posterPath 필드에 매핑
    private String posterPath; // 포스터 경로
}
