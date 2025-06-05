package com.example.backend.review;

import com.example.backend.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA 엔티티 클래스임을 나타냄
@Getter // Getter 자동 생성
@Setter // Setter 자동 생성
@NoArgsConstructor // 파라미터 없는 기본 생성자 자동 생성
public class Review {

    @Id // 기본 키 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략
    private Long id;

    @Column(nullable = false)
    private Long movieId;  // TMDB 영화 ID 저장

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계, 지연 로딩 방식
    @JoinColumn(name = "user_id", nullable = false) // 외래키 컬럼명 지정, null 불가
    private User writer; // 리뷰 작성자(User 엔티티 참조)

    @Column(nullable = false)
    private LocalDateTime dateTime; // 리뷰 작성 시각

    @Column(length = 1000, nullable = false) // 길이 제한 1000, null 불가
    private String content; // 리뷰 내용

    @Column
    private Long rating; // 리뷰 평점

    // 모든 필드를 초기화하는 생성자
    public Review(Long movieId, User writer, String content, Long rating, LocalDateTime dateTime) {
        this.movieId = movieId;
        this.writer = writer;
        this.content = content;
        this.rating = rating;
        this.dateTime = dateTime;
    }
}
