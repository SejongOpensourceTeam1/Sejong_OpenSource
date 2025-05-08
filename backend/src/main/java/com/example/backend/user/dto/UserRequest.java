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
    private String email;
    private String password;

    public User toEntity() {
        return User.builder()
                .nickname(this.nickname)
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
