package com.example.backend.movie;

import com.example.backend.movie.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // 기본 페이지 (홈페이지) 매핑
    @GetMapping("/")
    public String home() {
        return "index";  // 홈 페이지로 'index.html' 템플릿을 사용
    }

    // 영화 검색 페이지 매핑
    @GetMapping("/movies/search")
    public String searchMovies(@RequestParam String query, Model model) {
        List<MovieDto> movies = movieService.searchMovies(query);
        model.addAttribute("movies", movies);
        return "movieSearch";  // 영화 검색 결과 페이지 템플릿
    }
}
