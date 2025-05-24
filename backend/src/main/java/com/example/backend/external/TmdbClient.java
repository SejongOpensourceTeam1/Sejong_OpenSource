package com.example.backend.external;

import com.example.backend.movie.Movie;
import com.example.backend.movie.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TmdbClient {

    private final RestTemplate restTemplate;

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.base-url}")
    private String baseUrl;

    public Movie fetchMovieById(Long movieId) {
        String url = baseUrl + "/movie/" + movieId + "?api_key=" + apiKey + "&language=ko-KR";

        MovieDto movieDto = restTemplate.getForObject(url, MovieDto.class);

        if (movieDto == null) {
            return null;
        }

        return Movie.builder()
                .id(movieDto.getId())
                .title(movieDto.getTitle())
                .rating(Double.valueOf(movieDto.getRating()))
                .posterPath("https://image.tmdb.org/t/p/w500" + movieDto.getPosterPath())
                .build();
    }
}
