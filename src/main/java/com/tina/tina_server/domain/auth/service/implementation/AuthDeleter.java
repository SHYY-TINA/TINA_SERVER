package com.tina.tina_server.domain.auth.service.implementation;

import com.tina.tina_server.domain.auth.domain.Token;
import org.springframework.stereotype.Service;

@Service
public class AuthDeleter {
    public void deleteRefreshToken(Token token) {
        token.deleteRefreshToken();
    }
}