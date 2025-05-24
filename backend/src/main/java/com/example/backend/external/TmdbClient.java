package com.example.backend.external;

import com.example.backend.movie.Movie;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TmdbClient {

    private final RestTemplate restTemplate;
    private final String apiKey = "YOUR_TMDB_API_KEY";
    private final String tmdbApiUrl = "https://api.themoviedb.org/3/movie/";

    public TmdbClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Movie fetchMovieById(Long movieId) {
        String url = tmdbApiUrl + movieId + "?api_key=" + apiKey + "&language=ko-KR";

        MovieDto movieDTO = restTemplate.getForObject(url, MovieDto.class);

        if (movieDTO == null) {
            return null;
        }

        Movie movie = new Movie();
        movie.setImdbCode(movieDTO.getImdbId());
        movie.setTitle(movieDTO.getTitle());
        movie.setDirector("");  // 필요하면 별도 API 호출
        movie.setReleaseDate(LocalDate.parse(movieDTO.getReleaseDate()));
        movie.setCoverImage("https://image.tmdb.org/t/p/w500" + movieDTO.getPosterPath());

        return movie;
    }
}
