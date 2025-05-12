package com.example.backend.apiCallTest.repository;

import com.example.backend.apiCallTest.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(Review review) {
        String sql = "INSERT INTO review (movie_id, username, content, rating) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, review.getMovieId(), review.getUsername(), review.getContent(), review.getRating());
    }

    public List<Review> findByMovieId(Long movieId) {
        String sql = "SELECT * FROM review WHERE movie_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Review.class), movieId);
    }

    public void update(Review review) {
        String sql = "UPDATE review SET content = ?, rating = ? WHERE id = ?";
        jdbcTemplate.update(sql, review.getContent(), review.getRating(), review.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM review WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
