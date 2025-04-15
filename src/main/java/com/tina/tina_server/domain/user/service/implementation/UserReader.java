package com.tina.tina_server.domain.user.service.implementation;

import com.tina.tina_server.domain.user.domain.repository.UserRepository;
import com.tina.tina_server.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tina.tina_server.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserReader {
    private final UserRepository userRepository;
    public Users findBySocialAccountUid(String id) {
        return userRepository.findBySocialAccountUid(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public Users findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
