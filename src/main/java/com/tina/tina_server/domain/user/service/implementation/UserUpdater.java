package com.tina.tina_server.domain.user.service.implementation;

import com.tina.tina_server.domain.user.domain.Users;
import com.tina.tina_server.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdater {

    private final UserRepository userRepository;

    public void updateDetail(Users user, String nickname, String mbti) {
        user.updateDetail(nickname, mbti);
        userRepository.save(user);
    }

}
