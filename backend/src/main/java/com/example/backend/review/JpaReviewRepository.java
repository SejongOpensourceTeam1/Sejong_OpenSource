package com.example.backend.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser_Id(Long userId);
}
