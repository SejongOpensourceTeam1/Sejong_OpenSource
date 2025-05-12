package com.example.backend.auth;

import com.example.backend.global.provider.JwtTokenProvider;
import com.example.backend.user.UserRepository;
import com.example.backend.user.dto.UserRequest;
import com.example.backend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponse register(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return new UserResponse(userRepository.save(userRequest.toEntity()));
    }

    /*
        @Transactional
    public AuthResponse login(AuthRequest authRequest) {
    }
     */
}
