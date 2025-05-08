package com.example.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user){
        User save = userRepository.save(user);
        System.out.println("User : " +  save);
    }

    public User updateUser(Long id, User newUser){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그런 사람 없는뎅"));

        // TODO : 유저 정보 수정

        System.out.println("User : " +  user);

        return userRepository.save(user);
    }


}

