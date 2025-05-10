package com.example.backend.user;

import com.example.backend.user.dto.UserRequest;
import com.example.backend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse post(@RequestBody UserRequest request) {
        User savedUser = userService.createUser(request);

        return UserResponse.builder()
                .id(savedUser.getId())
                .nickname(savedUser.getNickname())
                .build();
    }

    @GetMapping
    public UserResponse get(@RequestParam Long id) {
        User user = userService.getUser(id);

        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }

    @PutMapping("/{id}/username")
    public UserResponse updateUsername(@PathVariable Long id, @RequestBody UserRequest request) {
        User updatedUser = userService.updateUsername(id, request);

        return UserResponse.builder()
                .id(updatedUser.getId())
                .nickname(updatedUser.getNickname())
                .build();
    }

    @PutMapping("/{id}/nickname")
    public UserResponse updateUserNickname(@PathVariable Long id, @RequestBody UserRequest request) {
        User updatedUser = userService.updateUserNickname(id, request);

        return UserResponse.builder()
                .id(updatedUser.getId())
                .nickname(updatedUser.getNickname())
                .build();
    }

    @PutMapping("/{id}/password")
    public UserResponse updateUserPassword(@PathVariable Long id, @RequestBody UserRequest request) {
        User updatedUser = userService.updateUserPassword(id, request);

        return UserResponse.builder()
                .id(updatedUser.getId())
                .nickname(updatedUser.getNickname())
                .build();
    }

}
