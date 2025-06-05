package com.example.backend.movie;

import jakarta.persistence.*;
import lombok.*;

@Entity // JPA에서 이 클래스가 엔티티임을 명시
@Table(name = "movie") // 매핑될 테이블 이름을 "movie"로 지정
@Getter // 필드에 대한 getter 메서드 자동 생성
@Setter // 필드에 대한 setter 메서드 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
@Builder // 빌더 패턴을 사용할 수 있도록 설정
public class Movie {

    @Id // 기본 키(primary key)로 지정
    private Long id; // TMDB 영화 고유 ID

    @Column(nullable = false) // not null 제약 조건 설정
    private String title; // 영화 제목

    @Column(name = "poster_path") // DB 컬럼 이름을 "poster_path"로 매핑
    private String posterPath; // 포스터 경로

    @Column(name = "rating", precision = 3) // DB 컬럼 이름 지정 및 정밀도 설정
    private Number rating; // 평점 (예: 8.3)
}
