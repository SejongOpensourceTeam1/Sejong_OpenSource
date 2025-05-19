package com.example.backend.review;

import com.example.backend.review.dto.UserReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final JpaReviewRepository jpaReviewRepository;

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

    public List<UserReviewResponse> getMyReviewMovies(Long userId){

        List<Review> reviews = jpaReviewRepository.findByUser_Id(userId);

        return reviews.stream()
                .map(review -> new UserReviewResponse(
                        review.getMovie().getId(),
                        review.getMovie().getTitle(),
                        review.getMovie().getCoverImage(),
                        review.getRating(),
                        review.getContent()
                ))
                .toList();
    }
}

