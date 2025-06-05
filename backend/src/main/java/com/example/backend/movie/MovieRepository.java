package com.example.backend.movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Movie 엔티티에 대한 기본적인 CRUD 메서드를 제공하는 JPA 레포지토리
    // 기본 키 타입은 Long (영화 ID)
}
