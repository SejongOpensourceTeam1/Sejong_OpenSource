package com.example.backend.review;

import com.example.backend.movie.Movie;
import com.example.backend.movie.MovieRepository;
import com.example.backend.review.Review;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    /*
    public Review create(Long movieId, Long userId, String content) {
        Movie movie = movieRepository.findByMovieId(movieId)
                .orElseThrow(() -> new IllegalArgumentException("영화가 존재하지 않습니다"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));

        Review review = new Review(movie, user, content);
        return reviewRepository.save(review);
    }
    */

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public List<Review> getByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    @Transactional
    public Review update(Long id, String newContent) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다"));
        review.setContent(newContent);
        return review;
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
