package com.example.backend;

import com.example.backend.auth.dto.AuthRequestDto;
import com.example.backend.global.provider.JwtTokenProvider;
import com.example.backend.movie.Movie;
import com.example.backend.movie.MovieRepository;
import com.example.backend.review.Review;
import com.example.backend.review.ReviewRepository;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static String accessToken;
    private static User testUser;

    @Value("${tmdb.api-key}")
    String apiKey;

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
                .body("poster_path", equalTo(posterPath))
                .body("vote_average", equalTo(rating.floatValue()));
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

    @Test
    void fetchMovieFromTmdb() {
        // TMDB API KEY와 예시 영화 ID (e.g., 550 for Fight Club)
        Long movieId = 550L;

        // TMDB API URL
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey + "&language=ko-KR";

        // RestTemplate 객체로 HTTP GET 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // 결과 출력
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());
    }

    @Test
    void getReviewedMoviesByUserIdTest() {
        // 영화 2개 저장
        Movie movie1 = saveMovie(1001L, "영화1", "/poster1.jpg", 7.8);
        Movie movie2 = saveMovie(1002L, "영화2", "/poster2.jpg", 8.2);

        List<Movie> allMovies = movieRepository.findAll();
        System.out.println("All movies in DB: " + allMovies.size());  // 2 이상이어야 함

        // 유저가 영화1에 작성한 리뷰 저장
        Review review = new Review(
                movie1.getId(),
                testUser,
                "재밌어요!",
                5L,
                LocalDateTime.now()
        );

        Review savedReview = reviewRepository.save(review);
        System.out.println("Saved Review ID: " + savedReview.getId());
        System.out.println("Saved Review writerId ID: " + savedReview.getWriter().getId());
        System.out.println("Saved Review movie ID: " + savedReview.getMovieId());

        given()
                .log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/movies/reviewed/{userId}", testUser.getUsername())
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(movie1.getId().intValue()))
                .body("[0].title", equalTo(movie1.getTitle()))
                .body("[0].poster_path", equalTo(movie1.getPosterPath()))
                .body("[0].vote_average", equalTo(movie1.getRating().floatValue()));
    }
}
