package com.example.backend.movie;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // 해당 클래스가 REST API의 컨트롤러임을 나타냄
@RequestMapping("/movies") // 기본 요청 경로를 "/movies"로 설정
@RequiredArgsConstructor // final 필드를 파라미터로 받는 생성자 자동 생성
public class MovieController {

    private final MovieRepository movieRepository; // 영화 정보를 데이터베이스에서 조회하기 위한 레포지토리
    private final MovieService movieService; // 영화 관련 비즈니스 로직을 처리하는 서비스 계층

    @GetMapping("/{id}") // GET /movies/{id} 요청을 처리
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        // 영화 ID로 DB에서 조회, 있으면 200 OK와 함께 반환, 없으면 404 Not Found
        return movieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reviewed/{userId}") // GET /movies/reviewed/{userId} 요청 처리
    public ResponseEntity<List<Movie>> getReviewedMoviesByUserId(@PathVariable String userId) {
        // 특정 유저가 작성한 리뷰에 해당하는 영화 목록을 조회
        List<Movie> movies = movieService.getMoviesReviewedByUserId(userId);
        System.out.println("내가 작성한 리뷰를 불러왔다 "); // 디버깅용 출력
        return ResponseEntity.ok(movies); // 영화 목록을 200 OK와 함께 반환
    }

}
