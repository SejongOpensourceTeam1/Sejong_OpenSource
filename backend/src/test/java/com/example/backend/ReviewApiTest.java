package com.example.backend;

import com.example.backend.auth.dto.AuthRequestDto;
import com.example.backend.review.dto.ReviewRequest;
import com.example.backend.review.dto.ReviewUpdateRequest;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import com.example.backend.global.provider.JwtTokenProvider;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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
public class ReviewApiTest {

    @LocalServerPort
    private int port; // 동적으로 서버 포트 주입

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

    private Long createReviewAndReturnId(Long movieId, String content, Long rating) {
        ReviewRequest createRequest = new ReviewRequest();
        createRequest.setMovieId(movieId);
        createRequest.setUsername(testUser.getUsername());
        createRequest.setContent(content);
        createRequest.setRating(rating);
        createRequest.setDateTime(LocalDateTime.of(2024, 5, 22, 15, 30)); // 현재 시간 또는 원하는 시간 지정 가능

        ExtractableResponse<Response> response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .body(createRequest)
                .when()
                .post("/reviews")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        return response.jsonPath().getLong("id");
    }

    @Test
    void createReviewTest() {
        ReviewRequest request = new ReviewRequest();
        request.setMovieId(1001L);
        request.setUsername(testUser.getUsername()); // testUser에서 가져온 username 사용
        request.setContent("영화 진짜 재밌음!");
        request.setRating(5L);
        request.setDateTime(LocalDateTime.of(2024, 5, 22, 15, 30)); // 원하는 날짜시간 세팅

        // 리뷰 생성 테스트
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)  // 토큰 헤더 추가
                .body(request)
                .log().all()  // 요청과 응답 로그 출력
                .when()
                .post("/reviews")
                .then()
                .log().all()  // 응답 로그 출력
                .statusCode(HttpStatus.OK.value())  // 응답 코드 확인
                .body("movieId", equalTo(1001))
                .body("writerId", equalTo(testUser.getId().intValue()))
                .body("content", equalTo("영화 진짜 재밌음!"))
                .body("rating", equalTo(5))
                .body("dateTime", equalTo("2024-05-22T15:30:00")); // dateTime 문자열 검증
    }

    @Test
    void getReviewsByMovieIdTest() {
        Long movieId = 2002L;
        Long reviewId = createReviewAndReturnId(movieId, "좋은 영화입니다.", 4L);

        // 특정 영화에 대한 리뷰 조회 테스트
        given()
                .header("Authorization", "Bearer " + accessToken)  // 토큰 헤더 추가
                .log().all()
                .when()
                .get("/reviews/movie/{movieId}", movieId)  // 경로에 movieId를 직접 넣기
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", hasItem(reviewId.intValue()));  // 리뷰 ID 확인
    }

    @Test
    void getReviewsByWriterIdTest() {
        Long writerId = testUser.getId(); // `testUser.getId()` 대신 고정된 값 사용
        Long reviewId = createReviewAndReturnId(3003L, "내가 쓴 리뷰", 4L);

        // 특정 작성자가 쓴 리뷰 조회 테스트
        given()
                .header("Authorization", "Bearer " + accessToken)  // 토큰 헤더 추가
                .log().all()
                .when()
                .get("/reviews/writer/{writerId}", writerId)  // 경로에 writerId를 직접 넣기
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", hasItem(reviewId.intValue()));  // 리뷰 ID 확인
    }

    @Test
    void updateReviewTest() {
        Long movieId = 4004L;
        Long reviewId = createReviewAndReturnId(movieId, "업데이트 전 내용", 2L);

        // 리뷰 업데이트 요청
        ReviewUpdateRequest updateRequest = new ReviewUpdateRequest();
        updateRequest.setContent("업데이트 후 내용");
        updateRequest.setRating(4L);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)  // 토큰 헤더 추가
                .body(updateRequest)
                .log().all()
                .when()
                .put("/reviews/{id}", reviewId)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("content", equalTo("업데이트 후 내용"))
                .body("rating", equalTo(4));  // 업데이트된 내용 확인
    }

    @Test
    void deleteReviewTest() {
        Long movieId = 5005L;
        Long reviewId = createReviewAndReturnId(movieId, "삭제할 리뷰", 1L);

        // 리뷰 삭제 요청
        given()
                .header("Authorization", "Bearer " + accessToken)  // 토큰 헤더 추가
                .log().all()
                .when()
                .delete("/reviews/{id}", reviewId)
                .then()
                .log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());  // 삭제 성공 확인
    }
}
