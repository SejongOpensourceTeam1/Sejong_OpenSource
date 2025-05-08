package com.example.backend.auth;

import com.example.backend.user.dto.UserRequest;
import com.example.backend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest requestDto) {
        return authService.register(requestDto);
    }

/*
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest requestDto) {
        return authService.login(requestDto);
    }
*/
}
