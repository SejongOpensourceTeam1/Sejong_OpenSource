package com.example.backend;

import com.example.backend.auth.dto.AuthRequestDto;
import com.example.backend.global.provider.JwtTokenProvider;
import com.example.backend.movie.Movie;
import com.example.backend.movie.MovieRepository;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MovieApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static String accessToken;
    private static User testUser;

    @BeforeEach
    void setup() {
        // 동적으로 할당된 포트를 RestAssured에서 사용
        RestAssured.port = port;

        // 로그인 요청
        AuthRequestDto loginRequest = new AuthRequestDto();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        // 로그인하여 accessToken 저장
        accessToken = given()
                .contentType("application/json")
                .body(loginRequest)
                .log().all()
                .when()
                .post("/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("accessToken");

        // 로그인한 사용자 정보 조회 (Optional)
        testUser = userRepository.findByUsername("testuser").orElseThrow();
    }

    private Movie saveMovie(Long id, String title, String posterPath, Double rating) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setPosterPath(posterPath);
        movie.setRating(rating);
        return movieRepository.save(movie);
    }

    @Test
    void getMovieByIdTest() {
        Long movieId = 12345L;
        String title = "테스트 영화";
        String posterPath = "/testPoster.jpg";
        Double rating = 8.5;

        saveMovie(movieId, title, posterPath, rating);

        given()
                .log().all()
                .header("Authorization", "Bearer " + accessToken)  // 토큰 헤더 추가
                .when()
                .get("/movies/{id}", movieId)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(movieId.intValue()))
                .body("title", equalTo(title))
                .body("posterPath", equalTo(posterPath))
                .body("rating", equalTo(rating.floatValue()));
    }

    @Test
    void getMovieById_NotFoundTest() {
        Long nonExistentMovieId = 999999L;

        given()
                .log().all()
                .header("Authorization", "Bearer " + accessToken)  // 토큰 헤더 추가
                .when()
                .get("/movies/{id}", nonExistentMovieId)
                .then()
                .log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
