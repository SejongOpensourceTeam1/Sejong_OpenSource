package com.example.backend.user.dto;

import com.example.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String nickname;
    private String username;
    private String password;

    public User toEntity() {
        return User.builder()
                .nickname(this.nickname)
                .username(this.username)
                .password(this.password)
                .build();
    }
}
