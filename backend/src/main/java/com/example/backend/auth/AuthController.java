package com.example.backend.auth;

import com.example.backend.auth.dto.AuthRequestDto;
import com.example.backend.auth.dto.AuthResponseDto;
import com.example.backend.user.dto.UserRequestDto;
import com.example.backend.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto requestDto) {
        UserResponseDto response = authService.register(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // TODO : 배포 시 삭제
    @GetMapping("/register")
    public String test1() {
        return "권한 테스트";
    }

    @GetMapping("/login")
    public String test2() {
        return "권한 테스트";
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        //authService.logout();
        return ResponseEntity.noContent().build();
    }

}
