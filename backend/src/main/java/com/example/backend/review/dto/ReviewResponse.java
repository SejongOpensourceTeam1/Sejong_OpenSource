package com.example.backend.review.dto;

import com.example.backend.review.Review;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
    private Long id;
    private Long movieId;
    private Long writerId;
    private String content;
    private Long rating;
    private LocalDateTime dateTime;

    public ReviewResponse() {
    }

    public ReviewResponse(Long id, Long movieId, Long writerId, String content, Long rating, LocalDateTime dateTime) {
        this.id = id;
        this.movieId = movieId;
        this.writerId = writerId;
        this.content = content;
        this.rating = rating;
        this.dateTime = dateTime;
    }

    public static ReviewResponse fromEntity(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getMovieId(),
                review.getWriter().getId(),
                review.getContent(),
                review.getRating(),
                review.getDateTime()
        );
    }

}
