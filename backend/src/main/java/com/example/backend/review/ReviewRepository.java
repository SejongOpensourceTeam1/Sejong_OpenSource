package com.example.backend.review;

import com.example.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 특정 영화 ID에 해당하는 리뷰 목록 조회
    List<Review> findByMovieId(Long movieId);

    // 특정 작성자(User)에 해당하는 리뷰 목록 조회
    List<Review> findByWriter(User writer);
}
