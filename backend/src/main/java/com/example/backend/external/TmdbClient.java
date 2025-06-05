package com.example.backend.external;

import com.example.backend.movie.Movie;
import com.example.backend.movie.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service // 스프링의 서비스 계층 컴포넌트로 등록
@RequiredArgsConstructor // final 필드를 파라미터로 받는 생성자를 자동 생성
public class TmdbClient {

    private final RestTemplate restTemplate; // 외부 API 요청을 위한 RestTemplate

    @Value("${tmdb.api-key}") // application.yml 또는 properties에서 API 키 주입
    private String apiKey;

    @Value("${tmdb.base-url}") // TMDB API의 기본 URL 주입
    private String baseUrl;

    // TMDB에서 영화 ID를 이용해 영화 정보를 가져오는 메서드
    public Movie fetchMovieById(Long movieId) {
        // API 요청 URL 구성 (한국어 응답 설정 포함)
        String url = baseUrl + "/movie/" + movieId + "?api_key=" + apiKey + "&language=ko-KR";

        // 외부 API 호출 후 응답을 MovieDto로 변환
        MovieDto movieDto = restTemplate.getForObject(url, MovieDto.class);

        // 응답이 없을 경우 null 반환
        if (movieDto == null) {
            return null;
        }

        // MovieDto를 내부 도메인 객체 Movie로 변환하여 반환
        return Movie.builder()
                .id(movieDto.getId()) // 영화 ID 설정
                .title(movieDto.getTitle()) // 영화 제목 설정
                .rating(movieDto.getRating()) // 영화 평점 설정
                .posterPath("https://image.tmdb.org/t/p/w500" + movieDto.getPosterPath()) // 포스터 전체 경로 구성
                .build();
    }
}
