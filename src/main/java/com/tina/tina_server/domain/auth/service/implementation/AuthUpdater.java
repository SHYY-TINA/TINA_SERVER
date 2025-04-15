package com.tina.tina_server.domain.auth.service.implementation;


import com.tina.tina_server.common.jwt.Jwt;
import com.tina.tina_server.common.jwt.dto.TokenResponse;
import com.tina.tina_server.domain.auth.domain.Token;
import com.tina.tina_server.domain.auth.domain.repository.TokenRepository;
import com.tina.tina_server.domain.user.domain.Users;
import com.tina.tina_server.domain.user.domain.vo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUpdater {
    private final Jwt jwt;
    private final TokenRepository tokenRepository;

    public TokenResponse publishToken(Users user, Token existingToken) {
        Jwt.Claims claims = Jwt.Claims.from(user.getId(), new Role[]{Role.USER});
        TokenResponse tokenResponse = jwt.generateAllToken(claims);

        if (existingToken != null) {
            existingToken.updateRefreshToken(tokenResponse.refreshToken());

        } else {
            existingToken = Token.builder()
                    .user(user)
                    .refreshToken(tokenResponse.refreshToken())
                    .build();
        }

        tokenRepository.save(existingToken);

        return tokenResponse;
    }

    public TokenResponse refreshToken(Token existingToken) {
        Users user = existingToken.getUser();

        Jwt.Claims claims = Jwt.Claims.from(user.getId(), new Role[]{Role.USER});
        TokenResponse tokenResponse = jwt.generateAllToken(claims);

        existingToken.updateRefreshToken(tokenResponse.refreshToken());

        tokenRepository.save(existingToken);

        return tokenResponse;
    }
}
