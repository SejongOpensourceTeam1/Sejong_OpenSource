package com.example.backend.movie;

import com.example.backend.movie.dto.MovieDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final RestTemplate restTemplate;

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.base-url}")
    private String baseUrl;

    public List<MovieDto> searchMovies(String query) {
        String url = String.format("%s/search/movie?api_key=%s&query=%s", baseUrl, apiKey, query);
        TmdbSearchResponse response = restTemplate.getForObject(url, TmdbSearchResponse.class);

        return response.getResults().stream().map(result -> {
            MovieDto dto = new MovieDto();
            dto.setId(result.getId());
            dto.setTitle(result.getTitle());
            dto.setOverview(result.getOverview());
            dto.setPosterPath(result.getPosterPath());
            return dto;
        }).collect(Collectors.toList());
    }

    private static class TmdbSearchResponse {
        private List<TmdbMovieResult> results;
        public List<TmdbMovieResult> getResults() { return results; }
        public void setResults(List<TmdbMovieResult> results) { this.results = results; }
    }

    private static class TmdbMovieResult {
        @Setter
        @Getter
        private Long id;
        @Setter
        @Getter
        private String title;
        @Setter
        @Getter
        private String overview;
        private String poster_path;

        public String getPosterPath() { return poster_path; }

        public void setPosterPath(String poster_path) { this.poster_path = poster_path; }
    }
}
