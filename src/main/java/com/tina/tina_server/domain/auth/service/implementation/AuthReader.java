package com.tina.tina_server.domain.auth.service.implementation;


import com.tina.tina_server.domain.auth.domain.Token;
import com.tina.tina_server.domain.auth.domain.repository.TokenRepository;
import com.tina.tina_server.domain.auth.exception.TokenNotFoundException;
import com.tina.tina_server.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthReader {
    private final TokenRepository tokenRepository;

    public Token findByUser(Optional<Users> user) {
        return tokenRepository.findByUser(user)
                .orElseThrow(TokenNotFoundException::new);
    }

    public Token findByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(TokenNotFoundException::new);
    }
}