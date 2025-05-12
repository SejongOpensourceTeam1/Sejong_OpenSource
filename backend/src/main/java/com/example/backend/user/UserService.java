package com.example.backend.user;

import com.example.backend.global.exception.UserNotFoundException;
import com.example.backend.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        return userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("그런 유저 없는뎅"));
    }

    public User updateUsername(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("그런 유저 없는뎅"));

        user.setUsername(request.getUsername());

        return userRepository.save(user);
    }

    public User updateUserNickname(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("그런 유저 없는뎅"));

        user.setNickname(request.getNickname());

        return userRepository.save(user);
    }

    public User updateUserPassword(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("그런 유저 없는뎅"));

        user.setPassword(request.getPassword()); // TODO: 암호화 필요

        return userRepository.save(user);
    }
}
