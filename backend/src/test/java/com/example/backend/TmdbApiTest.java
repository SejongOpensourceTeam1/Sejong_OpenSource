package com.example.backend;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.backend.movie.Movie;
import com.example.backend.movie.MovieRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class TmdbApiTest {

    @Autowired
    private MovieRepository movieRepository;

    // API KEY는 application.yml에서 불러오거나 직접 설정
    @Value("${tmdb.api-key}")
    String apiKey;

    @Test
    void fetchAndSaveMovieFromTmdb() throws Exception {
        Long movieId = 550L; // Fight Club

        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey + "&language=ko-KR";

        // 1. TMDB API 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 2. JSON 응답 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());


        String title = root.get("title").asText();
        String posterPath = root.get("poster_path").asText();
        double voteAverage = root.get("vote_average").asDouble();

        // 3. Movie 객체 생성
        Movie movie = Movie.builder()
                .id(movieId)
                .title(title)
                .posterPath("https://image.tmdb.org/t/p/w500" + posterPath)
                .rating(voteAverage)
                .build();

        // 4. 저장
        movieRepository.save(movie);

        // 5. 출력
        System.out.println("🎉 저장된 영화: " + movie);
    }
}
