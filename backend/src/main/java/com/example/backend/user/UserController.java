package com.example.backend.user;

import com.example.backend.user.dto.UserRequestDto;
import com.example.backend.user.dto.UserResponseDto;
import com.example.backend.user.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;


    /** 사용자 정보는 @AuthenticationPrincipal을 통해 안전하게 추출 */


    @GetMapping("/mypage")
    public UserResponseDto getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails)  {
        User user = userService.getUser(userDetails.getId());

        return UserResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .build();
    }

    @PutMapping("/mypage/modify/nickname")
    public UserResponseDto updateNickname(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestBody UserRequestDto request) {
        User user = userService.updateUserNickname(userDetails.getId(), request);
        return UserResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .build();
    }

    @PutMapping("/mypage/modify/password")
    public UserResponseDto updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestBody UserRequestDto request) {
        User user = userService.updateUserPassword(userDetails.getId(), request);
        return UserResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .build();
    }

}
