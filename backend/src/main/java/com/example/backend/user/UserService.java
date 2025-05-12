package com.example.backend.user;

import com.example.backend.global.exception.UserNotFoundException;
import com.example.backend.user.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("그런 유저 없는뎅"));
    }

    public User updateUserNickname(Long id, UserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("그런 유저 없는뎅"));

        user.setNickname(request.getNickname());

        return userRepository.save(user);
    }

    public User updateUserPassword(Long id, UserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("그런 유저 없는뎅"));

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }
}
