package com.tina.tina_server.domain.user.service.implementation;

import com.tina.tina_server.domain.user.domain.Users;
import com.tina.tina_server.domain.user.domain.repository.UserRepository;
import com.tina.tina_server.domain.user.domain.vo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreator {
    private final UserRepository userRepository;

    public Users signup(String userId, String profileImageUrl) {
        Users user = Users.builder()
                .socialAccountUid(userId)
                .profileImageUrl(profileImageUrl)
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }
}