package com.example.backend.apiCallTest.service;

import com.example.backend.apiCallTest.domain.Review;
import com.example.backend.apiCallTest.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void create(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> findByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    public void update(Review review) {
        reviewRepository.update(review);
    }

    public void delete(Long id) {
        reviewRepository.delete(id);
    }
}
