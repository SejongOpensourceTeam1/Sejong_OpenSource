package com.example.backend.user.dto;

import com.example.backend.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String nickname;
    private String username;
    private String email;

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.nickname = entity.getNickname();
    }
}
