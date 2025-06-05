package com.example.backend.user.security;

import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("해당 없음"));

        return new CustomUserDetails(user);
    }

    public UserDetails loadUserByUserId(Long userId) throws IllegalArgumentException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 없음"));

        return new CustomUserDetails(user);
    }
}
