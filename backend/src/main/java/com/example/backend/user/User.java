package com.example.backend.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String username;  // 유저가 쓰는 아이디

    @Column(length = 50, nullable = false, unique = true)
    private String nickname;  // 유저 닉네임

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 1000, nullable = true)
    private String refreshToken;

}
